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
import com.lynn.config.api.dto.SystemModuleDto;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.SystemModuleManager;
import com.lynn.config.api.manager.ZkDataPushManager;
import com.lynn.config.service.service.ActiveSystemVariableService;
import com.lynn.config.service.service.StaticSystemVariableService;
import com.lynn.config.service.service.SystemModuleService;
import com.lynn.config.api.utils.SystemUtils;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.framework.dto.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SystemModuleManagerImpl implements SystemModuleManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemModuleManagerImpl.class);

	@Resource
	private SystemModuleService systemModuleService;

	@Resource
	private StaticSystemVariableService staticSystemVariableService;

	@Resource
	private ActiveSystemVariableService activeSystemVariableService;

	@Resource
	private ZkDataPushManager zkDataPushManager;

	@Override
	public List<String> getSonSystemModuleNameByModuleNameAndFlag(SystemModule systemModule) {
		List<String> nameList = systemModuleService.getSonSystemModuleNameByCondition(systemModule);
		return nameList;
	}

	@Override
	public ResponseEntity updateStatus(SystemModuleDto systemModuleDto) {
		return systemModuleService.updateStatus(systemModuleDto);
	}

	@Override
	@Transactional(readOnly = false)
	public void addOrUpdateSystemModule(SystemModuleDto systemModuleDto) {
		SystemModule systemModule = new SystemModule();
		systemModule.setId(systemModuleDto.getId());
		systemModule.setModuleName(systemModuleDto.getModuleName());
		systemModule.setParentModuleId(systemModuleDto.getParentModuleId());
		systemModule.setModuleRemark(systemModuleDto.getModuleRemark());
		systemModule.setFlag(systemModuleDto.getFlag());
		systemModule.setStatus(systemModuleDto.getStatus());
		// 判断数据库中是否存在该记录，如果不存在，执行新增操作，如果已经存在，执行更新操作
		if (ValidateUtil.isNullOrEmpty(systemModuleService.getById(systemModuleDto.getId()))) {
			// 判断parentModuleName是否为空，如果不为空，即新增模块。如果为空，即新增系统，父id设置为“0”
			if (systemModuleDto.getParentModuleName() != null && systemModuleDto.getParentModuleName() != "") {
				systemModule.setParentModuleId(systemModuleService.getParentId(systemModuleDto).getId());
				SystemModule findParam = new SystemModule();
				findParam.setModuleName(systemModule.getModuleName());
				findParam.setParentModuleId(systemModule.getParentModuleId());
				List<SystemModule> list = systemModuleService.getByObj(findParam);
				if (!ValidateUtil.isNullOrEmpty(list)) {
					throw new CommonException(ExceptionConstants.Param.MODULE_EXISTS, ExceptionConstants.Param.MODULE_EXISTS_MSG);
				}
				// 自动生成模块id
				String id = null;
				if (systemModuleService.getMaxId(systemModule.getParentModuleId()) == null) {
					id = String.valueOf(Integer.parseInt(systemModule.getParentModuleId()) + 1);
				} else {
					id = String.valueOf((Integer.parseInt(systemModuleService.getMaxId(systemModule.getParentModuleId())) + 1));
				}
				systemModule.setId(id);
			} else {
				// 判断是否已有此数据
				SystemModule findParam = new SystemModule();
				findParam.setModuleName(systemModuleDto.getModuleName());
				findParam.setParentModuleId("0");
				List<SystemModule> list = systemModuleService.getByObj(findParam);
				if (!ValidateUtil.isNullOrEmpty(list)) {
					throw new CommonException(ExceptionConstants.Param.SYSTEM_EXISTS, ExceptionConstants.Param.SYSTEM_EXISTS_MSG);
				}
				systemModule.setParentModuleId("0");
				String id = String.valueOf(Integer.parseInt(systemModuleService.getMaxId(systemModule.getParentModuleId())) + 1000);
				systemModule.setId(id);
			}
			systemModule.setStatus(DataStatus.NORMAL);
			systemModuleService.insert(systemModule);
		} else {
			// 判断是否已有此数据
			// SystemModule findParams = new SystemModule();
			// findParams.setId(systemModuleDto.getId());
			SystemModule module = systemModuleService.getById(systemModuleDto.getId());
			if (ValidateUtil.isNullOrEmpty(module)) {
				throw new CommonException(ExceptionConstants.Param.VARIABLE_NOT_EXISTS, ExceptionConstants.Param.VARIABLE_NOT_EXISTS_MSG);
			}
			// 判断传过来的status是否与数据库中一致，如果不一致，说明需要执行的是删除操作，需要同时将此模块下的所有参数置为失效。恢复时，也要恢复
			// 如果status跟数据库中一致，说明不是删除恢复操作，只是更新系统模块的描述
			if (systemModuleDto.getStatus() != module.getStatus() && systemModuleDto.getStatus() != null) {
				StaticSystemVariable staticSystemVariable = new StaticSystemVariable();
				staticSystemVariable.setSystemName(systemModuleDto.getParentModuleName());
				staticSystemVariable.setModuleName(systemModuleDto.getModuleName());
				staticSystemVariable.setStatus(systemModuleDto.getStatus());
				staticSystemVariableService.updateByModuleStatus(staticSystemVariable);
				ActiveSystemVariable activeSystemVariable = new ActiveSystemVariable();
				activeSystemVariable.setSystemName(systemModuleDto.getParentModuleName());
				activeSystemVariable.setModuleName(systemModuleDto.getModuleName());
				activeSystemVariable.setStatus(systemModuleDto.getStatus());
				activeSystemVariableService.updateByModuleStatus(activeSystemVariable);
			}
			systemModuleService.update(systemModule);

		}
		SystemModule findParams = new SystemModule();
		findParams.setModuleName(systemModuleDto.getModuleName());
		List<SystemModule> list = systemModuleService.getByObj(findParams);
		// 判断是否可以推送到zk
		if (SystemUtils.canPush2ZK(systemModuleDto.getParentModuleName())) {
			//推送zk
			try {
				if (systemModuleDto.getStatus() == DataStatus.DELETED) {
					zkDataPushManager.delSystemModuleToZk(list);
				} else {
					zkDataPushManager.pushSystemModuleToZk(list);
					StaticSystemVariable staticSystemVariable = new StaticSystemVariable();
					staticSystemVariable.setSystemName(systemModuleDto.getParentModuleName());
					staticSystemVariable.setModuleName(systemModuleDto.getModuleName());
					List<StaticSystemVariable> listStatic = staticSystemVariableService.getByObj(staticSystemVariable);
					if (listStatic == null || listStatic.isEmpty()) {
						listStatic = new LinkedList<StaticSystemVariable>();
						listStatic.add(staticSystemVariable);
					}
					zkDataPushManager.pushListToZk(listStatic);
					ActiveSystemVariable activeSystemVariable = new ActiveSystemVariable();
					activeSystemVariable.setSystemName(systemModuleDto.getParentModuleName());
					activeSystemVariable.setModuleName(systemModuleDto.getModuleName());
					List<ActiveSystemVariable> listActive = activeSystemVariableService.getByObj(activeSystemVariable);
					if (listActive == null || listActive.isEmpty()) {
						listActive = new LinkedList<ActiveSystemVariable>();
						listActive.add(activeSystemVariable);
					}
					zkDataPushManager.pushListToZk(listActive);
				}
			} catch (Exception e) {
				LOGGER.info("推送到ZK失败,推送内容为{}", list, e);
				throw new CommonException(ExceptionConstants.ZooKeeper.ZK_SYNCHRONIZE_FAIL, ExceptionConstants.ZooKeeper.ZK_SYNCHRONIZE_FAIL_MSG);
			}
		}
	}

	@Override
	public PageInfo<SystemModuleDto> getSystemModuleByCondition(SystemModuleDto systemModuleDto, Pagenation pagenation) {

		return systemModuleService.getSystemModuleByCondition(systemModuleDto, pagenation);
	}

	@Override
	public List<String> getSystemName(SystemModuleDto systemModuleDto) {
		return systemModuleService.getSystemName(systemModuleDto);
	}

	public List<String> getAllSystemName() {
		List<String> list = systemModuleService.getAllSystemName();
		return list;
	}

	@Override
	public SystemModuleDto getParentId(SystemModuleDto systemModuleDto) {
		return systemModuleService.getParentId(systemModuleDto);
	}

	@Override
	public List<String> getAllSystemNameNotModuleStatus(ModuleStatus moduleStatus) {
		List<String> list = systemModuleService.getAllSystemNameNotModuleStatus(moduleStatus);
		return list;
	}
}
