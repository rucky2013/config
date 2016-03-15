/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.service.manager;

import com.mobanker.config.service.dto.PageInfo;
import com.mobanker.config.service.dto.Pagenation;
import com.mobanker.config.service.entity.StaticSystemVariable;
import com.mobanker.config.service.exception.CommonException;

import java.util.Map;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.manager
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
