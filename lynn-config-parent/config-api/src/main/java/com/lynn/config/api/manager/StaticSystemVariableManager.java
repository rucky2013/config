/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.manager;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.config.api.exception.CommonException;

import java.util.Map;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.manager
 * @ClassName:StaticSystemVariableManager
 * @author xiongweitao
 * @date 2016年1月19日 上午10:54:34
 */
public interface StaticSystemVariableManager {
	/**
	 * 
	 * Description:根据条件分页查询静态系统变量
	 * 
	 * @param systemName
	 * @param pagenation
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月22日 下午5:54:51
	 */
	PageInfo<StaticSystemVariable> getStaticSystemVariableByCondition(StaticSystemVariable staticSystemVariable, Pagenation pagenation);

	public Map<String, Map<String, String>> pushTreeDataToZk(StaticSystemVariable staticSystemVariable) throws CommonException, Exception;

//	public Map<String, String> getZkConfigBySystem(StaticSystemVariableDto staticSystemVariable);

	/**
	 * 
	 * Description:新增或更新静态系统参数
	 * 
	 * @param staticSystemVariable
	 * @author xiongweitao
	 * @date 2016年1月27日 下午2:40:12
	 */
	void addOrUpdateSystemVariable(StaticSystemVariable staticSystemVariable);

	/**
	 * 
	 * Description:根据项目名称(例如:customer)获取静态系统参数
	 * 
	 * @param systemName
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 上午11:05:21
	 */
	Map<String, Object> getStaticSystemVariableBySystemName(String systemName);
}
