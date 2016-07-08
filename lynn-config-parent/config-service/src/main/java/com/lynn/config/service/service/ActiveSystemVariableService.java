/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.framework.service.BaseService;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service
 * @ClassName:ActiveSystemVariableService
 * @author xiongweitao
 * @date 2016年1月19日 上午10:37:02
 */
public interface ActiveSystemVariableService extends BaseService<ActiveSystemVariable> {
	/**
	 * 
	 * Description:根据项目名称(例如:customer)和数据状态获取动态系统参数
	 * 
	 * @param activeSystemVariable
	 * @return
	 * @author xiongweitao
	 * @param normal
	 * @date 2016年1月21日 下午1:43:05
	 */
	List<ActiveSystemVariable> getActiveSystemVariableBySystemNameAndStatus(ActiveSystemVariable activeSystemVariable);

	/**
	 * 
	 * Description:根据条件分页查询动态系统变量
	 * 
	 * @param activeSystemVariable
	 * @param pagenation
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 上午10:39:58
	 */
	PageInfo<ActiveSystemVariable> getActiveSystemVariableByCondition(ActiveSystemVariable activeSystemVariable, Pagenation pagenation);

	/**
	 * @Description:删除/恢复模块时，更新该模块下的所有动态变量
	 * @param staticSystemVariable
	 */
	void updateByModuleStatus(ActiveSystemVariable activeSystemVariable);

	List<ActiveSystemVariable> getZkConfigBySystem();
}
