/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.manager;

import java.util.Map;

public interface WebServiceManager {
	/**
	 * 
	 * Description:执行Get请求访问其他服务器
	 * 
	 * @param systemUrlNid
	 * @param path
	 * @param parameterMap
	 * @return
	 * @author xiongweitao
	 * @throws Exception
	 * @date 2016年3月3日 上午11:24:22
	 */
	Map<String, Object> doGet(String systemUrlNid, String path, Map<String, String> parameterMap) throws Exception;

	/**
	 * 
	 * Description:执行Post请求访问其他服务器
	 * 
	 * @param systemUrlNid
	 * @param path
	 * @param params
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月3日 下午6:18:45
	 */
	Map<String, Object> doPost(String systemUrlNid, String path, Map<String, String> params);

}
