/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service;

import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.dto.SystemModuleDto;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.framework.dto.ResponseEntity;
import com.lynn.framework.service.BaseService;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service
 * @ClassName:SystemModuleService
 * @author xiongweitao
 * @date 2016年1月20日 下午4:06:03
 */
public interface SystemModuleService extends BaseService<SystemModule> {
	/**
	 * 
	 * Description:根据父模块名称和子模块条件获取所有的子模块
	 * 
	 * @param systemModule
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 下午4:58:26
	 */
	List<String> getSonSystemModuleNameByCondition(SystemModule systemModule);

	/**
	 * Description:更新模块状态
	 * 
	 * @param systemModuleDto
	 * @return
	 * @author wangtong
	 * @date 2016年1月26日
	 */
	ResponseEntity updateStatus(SystemModuleDto systemModuleDto);

	/**
	 * Description:获取所有系统下对应的所有模块
	 * 
	 * @param systemModuleDto
	 * @return
	 * @author wangtong
	 * @date 2016年1月26日
	 */
	PageInfo<SystemModuleDto> getSystemModuleByCondition(SystemModuleDto systemModuleDto, Pagenation pagenation);

	/**
	 * Description:获取所有系统名称
	 * 
	 * @param systemModuleDto
	 * @return
	 * @author wangtong
	 * @date 2016年1月26日
	 */
	List<String> getSystemName(SystemModuleDto systemModuleDto);

	List<SystemModule> getAllSystems();

	/**
	 * 
	 * Description:获取 所有的有效系统名称
	 * 
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月29日 下午3:30:49
	 */
	List<String> getAllSystemName();

	/**
	 * Description:获取系统id，作为模块添加时的父id
	 * 
	 * @param systemModuleDto
	 * @return
	 * @author wangtong
	 * @date 2016年1月26日
	 */
	SystemModuleDto getParentId(SystemModuleDto systemModuleDto);

	/**
	 * 
	 * Description:根据系统名和模块名查找系统模块对象
	 * 
	 * @param systemName
	 * @param moduleName
	 * @param dataStatus
	 * @return
	 * @author xiongweitao
	 * @date 2016年2月19日 上午9:54:17
	 */
	SystemModule getSystemModuleBySystemNameAndModuleName(String systemName, String moduleName, String dataStatus);

	/**
	 * Description:获取某系统下最大的系统或者模块id，用于新增系统模块时自动生成id
	 * 
	 * @param parentModuleId
	 * @return
	 */
	String getMaxId(String parentModuleId);

	/**
	 * 
	 * Description:获取所有状态为非(静态/动态)的系统名
	 * 
	 * @param moduleStatus
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月1日 上午9:47:11
	 */
	List<String> getAllSystemNameNotModuleStatus(ModuleStatus moduleStatus);

}
