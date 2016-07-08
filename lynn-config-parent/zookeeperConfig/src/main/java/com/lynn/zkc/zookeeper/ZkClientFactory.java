/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.zkc.zookeeper;

import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.zkclient.IZkStateListener;
import com.github.zkclient.ZkClient;
import com.lynn.zkc.cache.CacheManager;

public class ZkClientFactory {

	private static ZkClient zkClient;

	private static final Logger LOGGER = LoggerFactory.getLogger(ZkClientFactory.class);

	public static ZkClient getZkClient() {
		if (zkClient == null) {
			try {
				String connectString = (String)CacheManager.getStaticMapValue("zk.connectString");
				String sessionTimeout = (String)CacheManager.getStaticMapValue("zk.sessionTimeout");
				String connectionTimeout = (String)CacheManager.getStaticMapValue("zk.connectionTimeout");
				if (connectString == null || connectString.isEmpty() || sessionTimeout == null || sessionTimeout.isEmpty()
						|| connectionTimeout == null || connectionTimeout.isEmpty()) {
					throw new RuntimeException("配置服务连接失败,application.properties中找不到zk.connectString/zk.sessionTimeout/zk.connectionTimeout信息");
				}
				zkClient = new ZkClient(connectString, Integer.parseInt(sessionTimeout), Integer.parseInt(connectionTimeout));
			} catch (Exception e) {
				LOGGER.error("ZOOKEEPER连接失败", e);
				zkClient = null;
			} finally {
				if (zkClient != null) {
					// 为zookeeper状态绑定watcher
					subscribeStateChanges();
				}
			}
		}
		return zkClient;
	}

	/**
	 * 
	 * 
	 * Description:给连接状态挂上watcher
	 * 
	 * @author xiongweitao
	 * @date 2016年1月20日 上午9:23:25
	 */
	private static void subscribeStateChanges() {
		ZkClientFactory.getZkClient().subscribeStateChanges(new IZkStateListener() {
			// zk连接状态改变
			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				if (state == null) {
					LOGGER.info("ZOOKEEPER连接状态改变");
				} else {
					LOGGER.info("ZOOKEEPER连接状态改变为" + state.name());
				}
			}

			// zk SESSIONTIMEOUT,重连
			@Override
			public void handleNewSession() throws Exception {
				LOGGER.info("SESSIONTIMEOUT,ZOOKEEPER重连,产生新SESSION");
			}
		});
	}
}
