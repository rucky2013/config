/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.manager.manager.impl;

import com.lynn.config.api.constants.CommonConstants;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.dto.StaticSystemVariableDto;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.ActiveSystemVariableManager;
import com.lynn.config.api.manager.ZkDataPushManager;
import com.lynn.config.service.service.ActiveSystemVariableService;
import com.lynn.config.service.service.SystemModuleService;
import com.lynn.config.api.utils.SystemUtils;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.config.api.utils.VariableTransformUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.manager.impl
 * @ClassName:ActiveSystemVariableManagerImpl
 * @author xiongweitao
 * @date 2016年1月19日 上午10:53:53
 */
@Service
@Transactional(readOnly = true)
public class ActiveSystemVariableManagerImpl implements ActiveSystemVariableManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveSystemVariableManagerImpl.class);

	@Resource
	private ActiveSystemVariableService activeSystemVariableService;

	@Resource
	private ZkDataPushManager zkDataPushManager;

	@Resource
	private SystemModuleService systemModuleService;

	@Override
	public PageInfo<ActiveSystemVariable> getActiveSystemVariableByCondition(ActiveSystemVariable activeSystemVariable, Pagenation pagenation) {
		return activeSystemVariableService.getActiveSystemVariableByCondition(activeSystemVariable, pagenation);
	}

	/**
	 * 方法 pushTreeDataToZk 功能描述 ：推送动态树、数据到zookeeper
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/26
	 * @param activeSystemVariable
	 * @return java.util.Map
	 * 
	 */
	@Override
	public Map<String, Map<String, String>> pushTreeDataToZk(ActiveSystemVariable activeSystemVariable) throws Exception {
		// 获取需要推送的数据
		if (ValidateUtil.isNullOrEmpty(activeSystemVariable)) {
			activeSystemVariable = new ActiveSystemVariable();
		}

		if ("".equals(activeSystemVariable.getSystemName())) {
			activeSystemVariable.setSystemName(null);
		}
		if ("".equals(activeSystemVariable.getModuleName())) {
			activeSystemVariable.setModuleName(null);
		}
		activeSystemVariable.setStatus(DataStatus.NORMAL);
		List<ActiveSystemVariable> listToPush = activeSystemVariableService.getByObj(activeSystemVariable);
		if (listToPush == null || listToPush.size() == 0) {
			return null;
		}
		return zkDataPushManager.pushListToZk(listToPush);
	}

	@Override
	@Transactional(readOnly = false)
	public void addOrUpdateSystemVariable(ActiveSystemVariable activeSystemVariable) {
		if (ValidateUtil.isNullOrEmpty(activeSystemVariable.getId())) {
			// 判断是否已有此数据
			ActiveSystemVariable findParams = new ActiveSystemVariable();
			findParams.setSystemName(activeSystemVariable.getSystemName());
			findParams.setNid(activeSystemVariable.getNid());
			List<ActiveSystemVariable> list = activeSystemVariableService.getByObj(findParams);
			if (!ValidateUtil.isNullOrEmpty(list)) {
				throw new CommonException(ExceptionConstants.Param.VARIABLE_EXISTS, ExceptionConstants.Param.VARIABLE_EXISTS_MSG);
			}
			// 如果ID为空,执行新增
			activeSystemVariable.setStatus(DataStatus.NORMAL);
			activeSystemVariableService.insert(activeSystemVariable);
		} else {
			// 判断是否已有此数据
			ActiveSystemVariable findParams = new ActiveSystemVariable();
			findParams.setId(activeSystemVariable.getId());
			List<ActiveSystemVariable> list = activeSystemVariableService.getByObj(findParams);
			if (ValidateUtil.isNullOrEmpty(list)) {
				throw new CommonException(ExceptionConstants.Param.VARIABLE_NOT_EXISTS, ExceptionConstants.Param.VARIABLE_NOT_EXISTS_MSG);
			}

			// 判断该模块是否有效,无效模块不允许更新,必须先生效该模块
			SystemModule systemModule = systemModuleService.getSystemModuleBySystemNameAndModuleName(list.get(0).getSystemName(), list.get(0)
					.getModuleName(), ModuleStatus.ACTIVE.name());
			if (ValidateUtil.isNullOrEmpty(systemModule)) {
				throw new CommonException(ExceptionConstants.System.SYSTEM_MODULE_DELETED, ExceptionConstants.System.SYSTEM_MODULE_DELETED_MSG);
			}

			// 如果ID不为空,执行更新
			activeSystemVariableService.update(activeSystemVariable);
		}
		// 推送到zookeeper
		if (ValidateUtil.isNullOrEmpty(activeSystemVariable.getSystemName()) || ValidateUtil.isNullOrEmpty(activeSystemVariable.getModuleName())) {
			activeSystemVariable = activeSystemVariableService.getById(activeSystemVariable.getId());
		}
		// 判断是否可以推送
		if (SystemUtils.canPush2ZK(activeSystemVariable.getSystemName())) {
			// 推送
			ActiveSystemVariable findParams = new ActiveSystemVariable();
			findParams.setSystemName(activeSystemVariable.getSystemName());
			findParams.setModuleName(activeSystemVariable.getModuleName());
			findParams.setStatus(DataStatus.NORMAL);
			List<ActiveSystemVariable> list = activeSystemVariableService.getByObj(findParams);
			if (list == null || list.isEmpty()) {
				list = new LinkedList<ActiveSystemVariable>();
				list.add(findParams);
			}
			try {
				zkDataPushManager.pushListToZk(list);
			} catch (Exception e) {
				LOGGER.info("同步到ZK失败,同步内容为{}", list, e);
				throw new CommonException(ExceptionConstants.ZooKeeper.ZK_SYNCHRONIZE_FAIL, ExceptionConstants.ZooKeeper.ZK_SYNCHRONIZE_FAIL_MSG);
			}
		}
	}

	@Override
	public Map<String, Map<String, Object>> getActiveSystemVariableBySystemName(String systemName) {
		ActiveSystemVariable activeSystemVariable = new ActiveSystemVariable();
		activeSystemVariable.setSystemName(systemName);
		activeSystemVariable.setStatus(DataStatus.NORMAL);
		List<ActiveSystemVariable> list = activeSystemVariableService.getActiveSystemVariableBySystemNameAndStatus(activeSystemVariable);
		if (ValidateUtil.isNullOrEmpty(list)) {
			return null;
		}
		Map<String, Map<String, Object>> activeMap = VariableTransformUtils.activeSystemVariable2MapGroupByModuleName(list);
		return activeMap;
	}

	@Override
	public String getThisSystemVariableByNid(String nid) {
		ActiveSystemVariable params = new ActiveSystemVariable();
		params.setSystemName(CommonConstants.SYSTEM_NAME);
		params.setNid(nid);
		params.setStatus(DataStatus.NORMAL);
		List<ActiveSystemVariable> la = activeSystemVariableService.getByObj(params);
		if (ValidateUtil.isNullOrEmpty(la)) {
			throw new CommonException(ExceptionConstants.System.SYSTEMVARIABLE_ERROR, ExceptionConstants.System.SYSTEMVARIABLE_ERROR_MSG);
		}
		return la.get(0).getValue();
	}

	@Override
	public Map<String, String> getZkConfigBySystem(StaticSystemVariableDto staticSystemVariable) {
		List<ActiveSystemVariable> result = activeSystemVariableService.getZkConfigBySystem();
		Map<String, String> returnMap = new HashMap<>();
		if (!ValidateUtil.isNullOrEmpty(result)) {
			for (ActiveSystemVariable s : result) {
				if (s.getValue().contains("$$$$")) {
					String tempValue = "";
					if (staticSystemVariable != null && !ValidateUtil.isNullOrEmpty(staticSystemVariable.getSSystem())
							&& !ValidateUtil.isNullOrEmpty(staticSystemVariable.getDSystem())) {
						if (!ValidateUtil.isNullOrEmpty(s.getName()) && s.getName().contains("STATIC")) {
							tempValue = StringUtils.replace(s.getValue(), "$$$$", staticSystemVariable.getSSystem());
						}
						if (!ValidateUtil.isNullOrEmpty(s.getName()) && s.getName().contains("ACTIVE")) {
							tempValue = StringUtils.replace(s.getValue(), "$$$$", staticSystemVariable.getDSystem());
						}
					}
					if (staticSystemVariable != null && !ValidateUtil.isNullOrEmpty(staticSystemVariable.getSystemName())) {
						tempValue = StringUtils.replace(s.getValue(), "$$$$", staticSystemVariable.getSSystem());
						tempValue = StringUtils.replace(s.getValue(), "$$$$", staticSystemVariable.getSystemName());
					}
					returnMap.put(s.getNid(), tempValue);
				} else {
					returnMap.put(s.getNid(), s.getValue());
				}
			}
			return returnMap;
		}
		return null;
	}

	@Override
	public List<ActiveSystemVariable> getZkConfig() {
		return activeSystemVariableService.getZkConfigBySystem();
	}
}
