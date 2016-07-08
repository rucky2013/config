/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

package com.lynn.zkc.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);

	private static Map<String, Object> staticMap;

	private static Map<String, Map<String, Object>> activeMap;

	/**
	 * 
	 * Description:获取静态系统变量集合
	 * 
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月19日 下午4:36:20
	 */
	public static Map<String, Object> getStaticMap() {
		if (staticMap == null) {
			staticMap = new HashMap<String, Object>();
		}
		return staticMap;
	}

	/**
	 * 
	 * Description:获取动态系统变量集合()
	 * 
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月19日 下午4:37:03
	 */
	public static Map<String, Map<String, Object>> getActiveMap() {
		if (activeMap == null) {
			activeMap = new HashMap<String, Map<String, Object>>();
		}
		return activeMap;
	}

	/**
	 * 
	 * Description:通过key获取静态系统变量值value(例如,jdbc/redis/mq相关)
	 * 
	 * @param key
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月19日 下午4:37:18
	 */
	public static Object getStaticMapValue(String key) {
		return getStaticMap().get(key);
	}

	/**
	 * 
	 * Description:通过key获取动态系统变量值value(例如:接口连接http://www.lynn.com)
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author xiongweitao
	 * @date 2016年1月19日 下午4:37:44
	 */
	public static Object getActiveMapValue(String key) {
		Object object = null;
		for (Entry<String, Map<String, Object>> entry : getActiveMap().entrySet()) {
			Map<String, Object> map = entry.getValue();
			if (map != null) {
				object = map.get(key);
				if (object != null) {
					break;
				}
			}
		}
		LOGGER.info("从CacheManager中获取ACTIVE数据,key为{},value为{}", key, object);
		return object;
	}
}
