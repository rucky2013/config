/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

package com.lynn.zkc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.github.zkclient.IZkDataListener;
import com.github.zkclient.ZkClient;
import com.lynn.zkc.constants.SystemConstants;
import com.lynn.zkc.util.HttpClientUtils;
import com.lynn.zkc.zookeeper.ZkClientFactory;

@SuppressWarnings("all")
public class InnitCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(InnitCache.class);

	/**
	 * 
	 * Description:初始化zk,和缓存
	 * 
	 * @param pro
	 * @return
	 * @throws Exception
	 * @author xiongweitao
	 * @date 2016年1月18日 上午9:26:23
	 */
	public void innitParam(Properties pro) throws Exception {
		// 将zk系统参数写入静态资源
		for (Entry entry : pro.entrySet()) {
			CacheManager.getStaticMap().put((String) entry.getKey(), entry.getValue());
		}
		if (ZkClientFactory.getZkClient() != null) {
			innitStaticMapByZk();
			innitActiveMapByZk();
		} else {
			innitStaticMapByServer();
			innitActiveMapByServer();
		}
	}

	/**
	 * 
	 * Description:通过ZK初始化动态参数
	 * 
	 * @throws Exception
	 * @author xiongweitao
	 * @date 2016年1月20日 下午7:55:04
	 */
	public void innitActiveMapByZk() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemConstants.DATAFROM, SystemConstants.ZKDATA);
		CacheManager.getActiveMap().put(SystemConstants.DATAFROM, map);
		LOGGER.info("ZOOKEEPER 初始化动态系统参数");
		ZkClient zkClient = ZkClientFactory.getZkClient();
		String activeNode = (String) CacheManager.getStaticMapValue("zk.node.active");
		if (zkClient == null || activeNode == null || activeNode.isEmpty()) {
			throw new RuntimeException("ZOOKEEPER 初始化动态系统参数失败,application.properties中找不到zk.node.active信息");
		}
		List<String> activeList = zkClient.getChildren(activeNode);
		if (activeList != null && !activeList.isEmpty()) {
			for (String node : activeList) {
				// 绑定watcher
				bindWatcher(activeNode, node);
				// 取节点数据
				byte[] data = zkClient.readData(activeNode + "/" + node, true);
				if (data != null && data.length > 0) {
					String result = new String(data, "UTF-8");
					if (result != null && !result.isEmpty()) {
						Map<String, Object> zMap = JSONObject.parseObject(result, Map.class);
						LOGGER.debug("ZOOKEEPER 初始化动态系统参数,节点为{},数据为{}", activeNode + "/" + node, JSONObject.toJSONString(zMap));
						CacheManager.getActiveMap().put(node.toLowerCase(), zMap);
					}
				}
			}
		}
	}

	/**
	 * 
	 * Description:绑定watcher
	 * 
	 * @param parentNode
	 * @param node
	 * @throws Exception
	 * @author xiongweitao
	 * @date 2016年1月18日 上午9:32:07
	 */
	private void bindWatcher(final String parentNode, final String node) throws Exception {
		LOGGER.debug("ZOOKEEPER 动态参数节点绑定WATCHER");

		ZkClientFactory.getZkClient().subscribeDataChanges(parentNode + "/" + node, new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				LOGGER.info("ZOOKEEPER 节点{}删除,同步至此项目", dataPath);
				CacheManager.getActiveMap().remove(node.toLowerCase());
			}

			@Override
			public void handleDataChange(String dataPath, byte[] data) throws Exception {
				LOGGER.info("ZOOKEEPER 动态参数节点{}修改,同步至此项目", dataPath);
				try {
					if (data != null && data.length > 0) {
						String result = new String(data, "UTF-8");
						if (result != null && !result.isEmpty()) {
							Map<String, Object> zMap = JSONObject.parseObject(result, Map.class);
							CacheManager.getActiveMap().put(node.toLowerCase(), zMap);
							LOGGER.debug("ZOOKEEPER 动态参数节点{}修改,修改后内容为{}", dataPath, JSONObject.toJSONString(zMap));
						}

					}
				} catch (Exception e) {
					LOGGER.error("ZOOKEEPER更新缓存数据失败", e);
				}
			}
		});
	}

	/**
	 * 
	 * Description:从ZOOKEEPER初始化静态资源
	 * 
	 * @throws Exception
	 * @author xiongweitao
	 * @date 2016年1月18日 上午9:27:41
	 */
	private void innitStaticMapByZk() throws Exception {
		LOGGER.info("从ZOOKEEPER中初始化静态系统参数");
		ZkClient zkClient = ZkClientFactory.getZkClient();
		String staticNode = (String) CacheManager.getStaticMapValue("zk.node.static");
		if (zkClient == null || staticNode == null || staticNode.isEmpty()) {
			throw new RuntimeException("ZOOKEEPER 初始化动态系统参数失败,application.properties中找不到zk.node.static信息");
		}
		List<String> staticList = zkClient.getChildren(staticNode);
		if (staticList != null && !staticList.isEmpty()) {
			for (String node : staticList) {
				byte[] data = zkClient.readData(staticNode + "/" + node, true);
				if (data != null && data.length > 0) {
					String result = new String(data, "UTF-8");
					if (result != null && !result.isEmpty()) {
						Map<String, Object> zMap = JSONObject.parseObject(result, Map.class);
						LOGGER.debug("从ZOOKEEPER中初始化静态系统参数,节点为{},数据为{}", staticNode + "/" + node, JSONObject.toJSONString(zMap));
						if (zMap != null || !zMap.isEmpty()) {
							for (Entry<String, Object> entry : zMap.entrySet()) {
								CacheManager.getStaticMap().put(entry.getKey(), entry.getValue());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * Description:通过配置服务初始化动态参数,该方法 允许本系统无动态系统参数
	 * 
	 * @author xiongweitao
	 * @throws Exception
	 * @date 2016年1月20日 下午7:54:40
	 */
	private void innitActiveMapByServer() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemConstants.DATAFROM, SystemConstants.SERVERDATA);
		CacheManager.getActiveMap().put(SystemConstants.DATAFROM, map);
		// 从服务中获取动态资源
		LOGGER.info("ZOOKEEPER连接失败,从配置服务中获取动态资源");
		String url = (String) CacheManager.getStaticMapValue("zk.disconnect.configServer");
		String activeNode = (String) CacheManager.getStaticMapValue("zk.node.active");
		if (url == null || activeNode == null || url.isEmpty() || activeNode.isEmpty()) {
			throw new RuntimeException("配置服务连接失败,application.properties中找不到zk.disconnect.configServer/zk.node.active信息");
		}
		// 动态节点必须为/***/系统名称/active,即最后两个节点为系统名称和active
		String path = activeNode.substring(activeNode.lastIndexOf("/", activeNode.lastIndexOf("/") - 1));
		LOGGER.debug("从配置服务中初始化动态系统参数,路径为{}", url + path);
		String result = HttpClientUtils.doGet(url + path, null);
		LOGGER.debug("从配置服务中初始化动态系统参数,路径为{},返回数据为{}", url + path, result);
		boolean flag = false;
		if (result != null || !result.isEmpty()) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject != null && SystemConstants.HTTPOK.equals((String) jsonObject.get("status"))) {
				jsonObject = jsonObject.getJSONObject("data");
				if (jsonObject != null && SystemConstants.HTTPOK.equals((String) jsonObject.get("resultStatus"))) {
					// 该方法允许本系统无动态系统参数,此时value即使为null,也不会报错
					flag = true;
					Map<String, Map<String, Object>> zMap = jsonObject.getObject("value", Map.class);
					if (zMap != null && !zMap.isEmpty()) {
						for (Entry<String, Map<String, Object>> entry : zMap.entrySet()) {
							CacheManager.getActiveMap().put(entry.getKey(), entry.getValue());
						}
					}
				}
			}
		}
		if (flag) {
			LOGGER.info("从配置服务中获取动态资源成功");
		} else {
			LOGGER.info("从配置服务中获取动态资源失败");
			throw new RuntimeException("ZOOKEEPER连接失败,从配置服务中获取动态资源失败");
		}
	}

	/**
	 * 
	 * Description:从服务中获取静态资源,该方法<<不>>允许本系统无静态系统参数
	 * 
	 * @throws Exception
	 * @author xiongweitao
	 * @date 2016年1月22日 下午4:29:28
	 */
	private void innitStaticMapByServer() throws Exception {
		// 从服务中获取静态资源
		LOGGER.info("ZOOKEEPER连接失败,从配置服务中获取静态资源");
		String url = (String) CacheManager.getStaticMapValue("zk.disconnect.configServer");
		String staticNode = (String) CacheManager.getStaticMapValue("zk.node.static");
		if (url == null || staticNode == null || url.isEmpty() || staticNode.isEmpty()) {
			throw new RuntimeException("配置服务连接失败,application.properties中找不到zk.disconnect.configServer/zk.node.static信息");
		}
		String path = staticNode.substring(staticNode.lastIndexOf("/", staticNode.lastIndexOf("/") - 1));
		LOGGER.debug("从配置服务中初始化静态系统参数,路径为{}", url + path);
		String result = HttpClientUtils.doGet(url + path, null);
		LOGGER.debug("从配置服务中初始化静态系统参数,路径为{},返回数据为{}", url + path, result);
		boolean flag = false;
		if (result != null || !result.isEmpty()) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject != null && SystemConstants.HTTPOK.equals((String) jsonObject.get("status"))) {
				jsonObject = jsonObject.getJSONObject("data");
				if (jsonObject != null && SystemConstants.HTTPOK.equals((String) jsonObject.get("resultStatus"))) {
					flag = true;
					Map<String, Object> zMap = jsonObject.getObject("value", Map.class);
					if (zMap != null && !zMap.isEmpty()) {
						for (Entry<String, Object> entry : zMap.entrySet()) {
							CacheManager.getStaticMap().put(entry.getKey(), entry.getValue());
						}
					}
				}
			}
		}
		if (flag) {
			LOGGER.info("从配置服务中获取静态资源成功");
		} else {
			LOGGER.info("从配置服务中获取静态资源失败");
			throw new RuntimeException("ZOOKEEPER连接失败,从配置服务中获取静态资源失败");
		}
	}
}
