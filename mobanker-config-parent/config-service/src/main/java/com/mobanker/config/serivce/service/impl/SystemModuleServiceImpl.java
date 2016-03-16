/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.serivce.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mobanker.config.serivce.service.SystemModuleService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.mobanker.config.api.constants.ExceptionConstants;
import com.mobanker.config.api.constants.enums.DataStatus;
import com.mobanker.config.api.constants.enums.ModuleStatus;
import com.mobanker.config.serivce.dao.SystemModuleDao;
import com.mobanker.config.api.dto.PageInfo;
import com.mobanker.config.api.dto.Pagenation;
import com.mobanker.config.api.dto.SystemModuleDto;
import com.mobanker.config.api.entity.SystemModule;
import com.mobanker.config.api.exception.CommonException;
import com.mobanker.config.api.utils.ValidateUtil;
import com.mobanker.framework.constant.Constants;
import com.mobanker.framework.dto.ResponseEntity;
import com.mobanker.framework.service.impl.BaseServiceImpl;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.service.impl
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
