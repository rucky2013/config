/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.dao;

import java.util.List;

import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.SystemModuleDto;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.framework.dao.BaseDao;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.dao
 * @ClassName:SystemModuleDao
 * @author xiongweitao
 * @date 2016年1月20日 下午4:05:32
 */
public interface SystemModuleDao extends BaseDao<SystemModule> {

	/**
	 * 
	 * Description:根据父模块名称和子模块条件查找所有的子模块
	 * 
	 * @param systemModule
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 下午5:01:42
	 */
	List<String> getSonSystemModuleNameByCondition(SystemModule systemModule);

	SystemModuleDto getByName(SystemModuleDto systemModuleDto);

	List<SystemModuleDto> getSystemModuleByCondition(SystemModuleDto systemModuleDto);

	List<String> getSystemName();

	void updateStatus(SystemModuleDto systemModuleDto);

	List<SystemModule> getAllSystems();

	/**
	 * 
	 * Description:获取所有的有效系统名称
	 * 
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月29日 下午3:32:16
	 */
	List<String> getAllSystemName();

	List<String> getSystemNameByPid(SystemModuleDto systemModuleDto);

	/**
	 * Description:根据系统名和模块名查找系统模块对象
	 * 
	 * @param systemName
	 * @param moduleName
	 * @param dataStatus
	 * @return
	 * @author xiongweitao
	 * @date 2016年2月19日 上午9:55:10
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
	 * Description:获取非(动态/静态)的项目名
	 * 
	 * @param moduleStatus
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月1日 上午9:53:10
	 */
	List<String> getAllSystemNameNotModuleStatus(ModuleStatus moduleStatus);

	SystemModuleDto getParentId(SystemModuleDto systemModuleDto);
}
