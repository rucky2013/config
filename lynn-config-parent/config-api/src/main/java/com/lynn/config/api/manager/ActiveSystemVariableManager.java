/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.manager;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.dto.StaticSystemVariableDto;
import com.lynn.config.api.entity.ActiveSystemVariable;

import java.util.List;
import java.util.Map;

/**
 * 
 * Description:分页查询动态系统参数
 * 
 * @PackageName:com.lynn.config.manager
 * @ClassName:ActiveSystemVariableManager
 * @author xiongweitao
 * @date 2016年1月19日 上午10:53:28
 */
public interface ActiveSystemVariableManager {

	/**
	 * 
	 * Description:根据条件分页查询动态系统变量
	 * 
	 * @param activeSystemVariable
	 * @param pagenation
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 上午10:10:08
	 */
	PageInfo<ActiveSystemVariable> getActiveSystemVariableByCondition(ActiveSystemVariable activeSystemVariable, Pagenation pagenation);

	public Map<String, Map<String, String>> pushTreeDataToZk(ActiveSystemVariable activeSystemVariable) throws Exception;

	/**
	 * 
	 * Description:新增或更新动态系统参数
	 * 
	 * @param activeSystemVariable
	 * @author xiongweitao
	 * @date 2016年1月27日 下午1:39:28
	 */
	void addOrUpdateSystemVariable(ActiveSystemVariable activeSystemVariable);

	/**
	 * 
	 * Description:根据项目名称(例如:customer)获取动态系统参数
	 * 
	 * @param systemName
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 下午12:03:08
	 */
	Map<String, Map<String, Object>> getActiveSystemVariableBySystemName(String systemName);

	/**
	 * 
	 * Description:根据nid查询本系统的动态参数value
	 * 
	 * @param nid
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月2日 下午4:44:32
	 */
	String getThisSystemVariableByNid(String nid);

	public Map<String, String> getZkConfigBySystem(StaticSystemVariableDto staticSystemVariable);

	/**
	 * 
	 * Description:获取zk的配置
	 * 
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月10日 下午4:48:27
	 */
	List<ActiveSystemVariable> getZkConfig();
}
