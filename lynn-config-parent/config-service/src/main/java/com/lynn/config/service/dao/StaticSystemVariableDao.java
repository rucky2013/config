/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.dao;

import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.framework.dao.BaseDao;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.dao
 * @ClassName:StaticSystemVariableDao
 * @author xiongweitao
 * @date 2016年1月19日 上午10:36:34
 */
public interface StaticSystemVariableDao extends BaseDao<StaticSystemVariable> {
	/**
	 * 
	 * Description:根据项目名和数据状态获取静态参数
	 * 
	 * @param staticSystemVariable
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 下午2:13:43
	 */
	List<StaticSystemVariable> getStaticSystemVariableBySystemNameAndStatus(StaticSystemVariable staticSystemVariable);

	/**
	 * 
	 * Description:根据条件分页查询静态系统变量
	 * 
	 * @param staticSystemVariable
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 上午10:47:19
	 */
	List<StaticSystemVariable> getStaticSystemVariableByCondition(StaticSystemVariable staticSystemVariable);

//	List<StaticSystemVariable> getZkConfigBySystem();

	
	
	/**
	 * @Description:
	 * @param staticSystemVariable
	 * @return
	 */
	void updateByModuleStatus(StaticSystemVariable staticSystemVariable);
}
