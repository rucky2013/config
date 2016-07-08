/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.dto.SystemModuleDto;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.config.service.dao.SystemModuleDao;
import com.lynn.config.service.service.SystemModuleService;
import com.lynn.framework.constant.Constants;
import com.lynn.framework.dto.ResponseEntity;
import com.lynn.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service.impl
 * @ClassName:SystemModuleServiceImpl
 * @author xiongweitao
 * @date 2016年1月20日 下午4:06:30
 */
@Service
public class SystemModuleServiceImpl extends BaseServiceImpl<SystemModule> implements SystemModuleService {

	@Resource
	private SystemModuleDao systemModuleDao;

	@Override
	public List<String> getSonSystemModuleNameByCondition(SystemModule systemModule) {
		List<String> list = systemModuleDao.getSonSystemModuleNameByCondition(systemModule);
		for (int i = 0; i < list.size(); i++) {
			if (!ValidateUtil.isNullOrEmpty(list.get(i))) {
				list.set(i, list.get(i).toLowerCase());
			}
		}
		return list;
	}

	@Override
	public ResponseEntity updateStatus(SystemModuleDto systemModuleDto) {
		ResponseEntity resp = new ResponseEntity();
		logger.info("updateStatus systemModuleDto:{}", systemModuleDto);
		SystemModule systemModule = getById(systemModuleDto.getId());
		resp.setStatus(Constants.System.OK);
		if (null == systemModule) {
			logger.error("更新系统模块表出错--没有查到记录!");
			throw new CommonException(ExceptionConstants.Param.VARIABLE_NOT_EXISTS, ExceptionConstants.Param.VARIABLE_NOT_EXISTS_MSG);
		}
		if (systemModuleDto.getStatus() == DataStatus.NORMAL) {
			systemModuleDto.setStatus(DataStatus.DELETED);
		} else {
			systemModuleDto.setStatus(DataStatus.NORMAL);
		}
		systemModuleDao.updateStatus(systemModuleDto);
		resp.setData(systemModuleDto);
		return resp;
	}

	@Override
	public PageInfo<SystemModuleDto> getSystemModuleByCondition(SystemModuleDto systemModuleDto, Pagenation pagenation) {

		PageHelper.startPage(pagenation.getPageNum(), pagenation.getPageSize());
		List<SystemModuleDto> list = systemModuleDao.getSystemModuleByCondition(systemModuleDto);
		PageInfo<SystemModuleDto> page = new PageInfo<SystemModuleDto>(list);
		return page;
	}

	@Override
	public List<String> getSystemName(SystemModuleDto systemModuleDto) {
		if (systemModuleDto.getParentModuleName() == null) {
			List<String> list = systemModuleDao.getSystemName();
			for (int i = 0; i < list.size(); i++) {
				if (!ValidateUtil.isNullOrEmpty(list.get(i))) {
					list.set(i, list.get(i).toLowerCase());
				}
			}
			return list;
		} else {
			systemModuleDto.setParentModuleId(systemModuleDao.getByName(systemModuleDto).getId());
			List<String> list = systemModuleDao.getSystemNameByPid(systemModuleDto);
			for (int i = 0; i < list.size(); i++) {
				if (!ValidateUtil.isNullOrEmpty(list.get(i))) {
					list.set(i, list.get(i).toLowerCase());
				}
			}
			return list;
		}
	}


	public List<String> getSystemName() {
		List<String> list = systemModuleDao.getSystemName();
		for (int i = 0; i < list.size(); i++) {
			if (!ValidateUtil.isNullOrEmpty(list.get(i))) {
				list.set(i, list.get(i).toLowerCase());
			}
		}
		return list;
	}

	public List<SystemModule> getAllSystems() {
		return systemModuleDao.getAllSystems();
	}

	@Override
	public List<String> getAllSystemName() {
		List<String> list = systemModuleDao.getAllSystemName();
		for (int i = 0; i < list.size(); i++) {
			if (!ValidateUtil.isNullOrEmpty(list.get(i))) {
				list.set(i, list.get(i).toLowerCase());
			}
		}
		return list;
	}

	@Override
	public SystemModuleDto getParentId(SystemModuleDto systemModuleDto) {
		return systemModuleDao.getParentId(systemModuleDto);
	}

	@Override
	public SystemModule getSystemModuleBySystemNameAndModuleName(String systemName, String moduleName, String dataStatus) {
		return systemModuleDao.getSystemModuleBySystemNameAndModuleName(systemName,moduleName,dataStatus);
	}

	@Override
	public String getMaxId(String parentModuleId) {
		return systemModuleDao.getMaxId(parentModuleId);
	}

	@Override
	public List<String> getAllSystemNameNotModuleStatus(ModuleStatus moduleStatus) {
		List<String> list = systemModuleDao.getAllSystemNameNotModuleStatus(moduleStatus);
		for (int i = 0; i < list.size(); i++) {
			if (!ValidateUtil.isNullOrEmpty(list.get(i))) {
				list.set(i, list.get(i).toLowerCase());
			}
		}
		return list;
	}

}
