/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.manager.manager.impl;

import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.StaticSystemVariableManager;
import com.lynn.config.api.manager.ZkDataPushManager;
import com.lynn.config.api.utils.SystemUtils;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.config.api.utils.VariableTransformUtils;
import com.lynn.config.service.service.StaticSystemVariableService;
import com.lynn.config.service.service.SystemModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.manager.impl
 * @ClassName:StaticSystemVariableManagerImpl
 * @author xiongweitao
 * @date 2016年1月19日 上午10:54:28
 */

@Service
@Transactional(readOnly = true)
@Path("staticSystem")
public class StaticSystemVariableManagerImpl implements StaticSystemVariableManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticSystemVariableManagerImpl.class);

	@Resource
	private StaticSystemVariableService staticSystemVariableService;

	@Resource
	private ZkDataPushManager zkDataPushManager;

	@Resource
	private SystemModuleService systemModuleService;

	@Override
	public PageInfo<StaticSystemVariable> getStaticSystemVariableByCondition(StaticSystemVariable staticSystemVariable, Pagenation pagenation) {
		return staticSystemVariableService.getStaticSystemVariableByCondition(staticSystemVariable, pagenation);
	}

	/**
	 * 方法 pushTreeDataToZk 功能描述
	 * ：推送静态树、数据到zookeeper，注释参考ActiveSystemVariableManagerImpl对应的方法
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/26
	 * @param staticSystemVariable
	 * @return java.util.Map
	 * @throws Exception
	 * 
	 */
	@Override
	public Map<String, Map<String, String>> pushTreeDataToZk(StaticSystemVariable staticSystemVariable) throws Exception {
		List<StaticSystemVariable> listToPush = new ArrayList<>();
		StaticSystemVariable findParams = new StaticSystemVariable();
		findParams.setStatus(DataStatus.NORMAL);
		if (ValidateUtil.isNullOrEmpty(staticSystemVariable) || ValidateUtil.isNullOrEmpty(staticSystemVariable.getSystemName())) {
			listToPush = staticSystemVariableService.getByObj(findParams);
		} else {
			findParams.setSystemName(staticSystemVariable.getSystemName());
			listToPush = staticSystemVariableService.getByObj(staticSystemVariable);
		}

		if (ValidateUtil.isNullOrEmpty(listToPush)) {
			return null;
		}
		return zkDataPushManager.RefreshStaticZk(listToPush);
	}

	// @Override
	// public Map<String, String> getZkConfigBySystem(StaticSystemVariableDto
	// staticSystemVariable) {
	// List<StaticSystemVariable> result =
	// staticSystemVariableService.getZkConfigBySystem();
	// Map<String, String> returnMap = new HashMap<>();
	// if (!ValidateUtil.isNullOrEmpty(result)) {
	// for (StaticSystemVariable s : result) {
	// if (s.getValue().contains("$$$$")) {
	// String tempValue = "";
	// if (staticSystemVariable != null &&
	// !ValidateUtil.isNullOrEmpty(staticSystemVariable.getSSystem())
	// && !ValidateUtil.isNullOrEmpty(staticSystemVariable.getDSystem())) {
	// if (!ValidateUtil.isNullOrEmpty(s.getName()) &&
	// s.getName().contains("STATIC")) {
	// tempValue = StringUtils.replace(s.getValue(), "$$$$",
	// staticSystemVariable.getSSystem());
	// }
	// if (!ValidateUtil.isNullOrEmpty(s.getName()) &&
	// s.getName().contains("ACTIVE")) {
	// tempValue = StringUtils.replace(s.getValue(), "$$$$",
	// staticSystemVariable.getDSystem());
	// }
	// }
	// if (staticSystemVariable != null &&
	// !ValidateUtil.isNullOrEmpty(staticSystemVariable.getSystemName())) {
	// tempValue = StringUtils.replace(s.getValue(), "$$$$",
	// staticSystemVariable.getSSystem());
	// tempValue = StringUtils.replace(s.getValue(), "$$$$",
	// staticSystemVariable.getSystemName());
	// }
	// returnMap.put(s.getNid(), tempValue);
	// } else {
	// returnMap.put(s.getNid(), s.getValue());
	// }
	// }
	// return returnMap;
	// }
	// return null;
	// }

	@Override
	@Transactional(readOnly = false)
	public void addOrUpdateSystemVariable(StaticSystemVariable staticSystemVariable) {
		if (ValidateUtil.isNullOrEmpty(staticSystemVariable.getId())) {
			// 判断是否已有此数据
			StaticSystemVariable findParams = new StaticSystemVariable();
			findParams.setSystemName(staticSystemVariable.getSystemName());
			findParams.setNid(staticSystemVariable.getNid());
			List<StaticSystemVariable> list = staticSystemVariableService.getByObj(findParams);
			if (!ValidateUtil.isNullOrEmpty(list)) {
				throw new CommonException(ExceptionConstants.Param.VARIABLE_EXISTS, ExceptionConstants.Param.VARIABLE_EXISTS_MSG);
			}
			// 如果ID为空,执行新增
			staticSystemVariable.setStatus(DataStatus.NORMAL);
			staticSystemVariableService.insert(staticSystemVariable);
		} else {
			// 判断是否已有此数据
			StaticSystemVariable findParams = new StaticSystemVariable();
			findParams.setId(staticSystemVariable.getId());
			List<StaticSystemVariable> list = staticSystemVariableService.getByObj(findParams);
			if (ValidateUtil.isNullOrEmpty(list)) {
				throw new CommonException(ExceptionConstants.Param.VARIABLE_NOT_EXISTS, ExceptionConstants.Param.VARIABLE_NOT_EXISTS_MSG);
			}

			// 判断该模块是否有效,无效模块不允许更新,必须先生效该模块
			SystemModule systemModule = systemModuleService.getSystemModuleBySystemNameAndModuleName(list.get(0).getSystemName(), list.get(0)
					.getModuleName(), ModuleStatus.STATIC.name());
			if (ValidateUtil.isNullOrEmpty(systemModule)) {
				throw new CommonException(ExceptionConstants.System.SYSTEM_MODULE_DELETED, ExceptionConstants.System.SYSTEM_MODULE_DELETED_MSG);
			}

			// 如果ID不为空,执行更新
			staticSystemVariableService.update(staticSystemVariable);
		}
		// 推送到zookeeper
		if (ValidateUtil.isNullOrEmpty(staticSystemVariable.getSystemName()) || ValidateUtil.isNullOrEmpty(staticSystemVariable.getModuleName())) {
			staticSystemVariable = staticSystemVariableService.getById(staticSystemVariable.getId());
		}
		// 判断是否可以推送到zk
		if (SystemUtils.canPush2ZK(staticSystemVariable.getSystemName())) {
			// 推送到zk
			StaticSystemVariable findParams = new StaticSystemVariable();
			findParams.setSystemName(staticSystemVariable.getSystemName());
			findParams.setModuleName(staticSystemVariable.getModuleName());
			findParams.setStatus(DataStatus.NORMAL);
			List<StaticSystemVariable> list = staticSystemVariableService.getByObj(findParams);
			if (list == null || list.isEmpty()) {
				list = new LinkedList<StaticSystemVariable>();
				list.add(findParams);
			}
			try {
				zkDataPushManager.pushListToZk(list);
			} catch (Exception e) {
				LOGGER.info("推送到ZK失败,推送内容为{}", list, e);
				throw new CommonException(ExceptionConstants.ZooKeeper.ZK_SYNCHRONIZE_FAIL, ExceptionConstants.ZooKeeper.ZK_SYNCHRONIZE_FAIL_MSG);
			}
		}
	}

	@Override
	@GET
	@Path("getSystem/{systemName}")
	@Produces({MediaType.APPLICATION_JSON})
//	@Produces({"*/*"})
	public Map<String, Object> getStaticSystemVariableBySystemName(@PathParam("systemName") String systemName) {
		StaticSystemVariable staticSystemVariable = new StaticSystemVariable();
		staticSystemVariable.setSystemName(systemName);
		staticSystemVariable.setStatus(DataStatus.NORMAL);
		List<StaticSystemVariable> list = staticSystemVariableService.getStaticSystemVariableBySystemNameAndStatus(staticSystemVariable);
		if (ValidateUtil.isNullOrEmpty(list)) {
			return null;
		}
		Map<String, Object> staticMap = VariableTransformUtils.staticSystemVariable2Map(list);
		return staticMap;
	}
}
