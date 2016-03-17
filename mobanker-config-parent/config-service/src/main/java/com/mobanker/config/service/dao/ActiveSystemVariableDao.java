/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.service.dao;

import com.mobanker.config.api.entity.ActiveSystemVariable;
import com.mobanker.framework.dao.BaseDao;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.dao
 * @ClassName:ActiveSystemVariableDao
 * @author xiongweitao
 * @date 2016年1月19日 上午10:35:29
 */
public interface ActiveSystemVariableDao extends BaseDao<ActiveSystemVariable> {
	/**
	 * 
	 * Description:根据项目名称和数据状态获取动态系统参数
	 * 
	 * @param moduleName
	 * @return
	 * @author xiongweitao
	 * @param normal
	 * @date 2016年1月21日 下午1:52:30
	 */
	List<ActiveSystemVariable> getActiveSystemVariableBySystemNameAndStatus(ActiveSystemVariable activeSystemVariable);

	/**
	 * 
	 * Description:根据条件查询动态系统变量
	 * 
	 * @param activeSystemVariable
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 上午10:51:04
	 */
	List<ActiveSystemVariable> getActiveSystemVariableByCondition(ActiveSystemVariable activeSystemVariable);

	/**
	 * @Description:删除/恢复模块时，更新该模块下的所有动态变量
	 * @param activeSystemVariable
	 */
	void updateByModuleStatus(ActiveSystemVariable activeSystemVariable);

	List<ActiveSystemVariable> getZkConfigBySystem();
}
