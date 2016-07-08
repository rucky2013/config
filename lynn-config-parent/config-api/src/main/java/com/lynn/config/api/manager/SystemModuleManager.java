package com.lynn.config.api.manager;

import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.dto.SystemModuleDto;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.framework.dto.ResponseEntity;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.bsw.manager
 * @ClassName:SystemModuleManager
 * @author xiongweitao
 * @date 2016年1月25日 下午4:39:39
 */
public interface SystemModuleManager {

	/**
	 * 
	 * Description:根据ModuleName获取所有有效子模块的名称
	 * 
	 * @param systemModule
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 下午4:45:47
	 */
	List<String> getSonSystemModuleNameByModuleNameAndFlag(SystemModule systemModule);

	/**
	 * Copyright @ 2013QIANLONG. All right reserved. className:
	 * Description:更新模块状态NORMAL/DELETED
	 * 
	 * @author wangtong
	 *
	 * @date 2016年1月26日
	 */
	ResponseEntity updateStatus(SystemModuleDto systemModuleDto);

	/**
	 * Copyright @ 2013QIANLONG. All right reserved. className:
	 * Description:查询所有系统下对应的所有子模块
	 * 
	 * @author wangtong
	 *
	 * @date 2016年1月26日
	 */
	PageInfo<SystemModuleDto> getSystemModuleByCondition(SystemModuleDto systemModuleDto, Pagenation pagenation);

	/**
	 * Copyright @ 2013QIANLONG. All right reserved. className:
	 * Description:查询所有系统名称
	 * 
	 * @author wangtong
	 *
	 * @date 2016年1月26日
	 */
	List<String> getSystemName(SystemModuleDto systemModuleDto);

	/**
	 * 
	 * Description:获取所有的项目名称
	 * 
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月27日 下午7:11:18
	 */
	List<String> getAllSystemName();

	/**
	 * Copyright @ 2013QIANLONG. All right reserved. className:
	 * Description:添加模块时，根据前台传入的系统名称查询id，作为次模块的parenModuleId
	 * 
	 * @author wangtong
	 *
	 * @date 2016年1月26日
	 */
	SystemModuleDto getParentId(SystemModuleDto systemModuleDto);

	public void addOrUpdateSystemModule(SystemModuleDto systemModuleDto);

	/**
	 * 
	 * Description:获取所有模块状态为非(静态/动态)的项目名
	 * 
	 * @param moduleStatus
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月1日 上午9:45:28
	 */
	List<String> getAllSystemNameNotModuleStatus(ModuleStatus moduleStatus);

}
