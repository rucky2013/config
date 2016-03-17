/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.manager.service;

import com.mobanker.config.api.dto.PageInfo;
import com.mobanker.config.api.dto.Pagenation;
import com.mobanker.config.api.entity.StaticSystemVariable;
import com.mobanker.framework.service.BaseService;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.service
 * @ClassName:StaticSystemVariableService
 * @author xiongweitao
 * @date 2016年1月19日 上午10:36:57
 */
public interface StaticSystemVariableService extends BaseService<StaticSystemVariable> {

	/**
	 * 
	 * Description:根据项目名称(例如:customer)和数据状态获取静态系统参数
	 * 
	 * @param staticSystemVariable
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 下午1:41:03
	 */
	List<StaticSystemVariable> getStaticSystemVariableBySystemNameAndStatus(StaticSystemVariable staticSystemVariable);

	/**
	 * 
	 * Description:根据条件分页查询静态系统变量
	 * 
	 * @param staticSystemVariable
	 * @param pagenation
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 上午10:47:46
	 */
	PageInfo<StaticSystemVariable> getStaticSystemVariableByCondition(StaticSystemVariable staticSystemVariable, Pagenation pagenation);

//	public List<StaticSystemVariable> getZkConfigBySystem();
	
	/**
	 * @Description:删除/恢复模块时，更新该模块下的所有静态变量
	 * @param staticSystemVariable
	 */
	void updateByModuleStatus(StaticSystemVariable staticSystemVariable);
}
