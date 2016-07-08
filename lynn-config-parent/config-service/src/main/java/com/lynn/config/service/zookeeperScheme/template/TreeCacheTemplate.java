package com.lynn.config.service.zookeeperScheme.template;

import com.lynn.config.service.zookeeperScheme.entity.ZkTree;
import com.lynn.config.service.zookeeperScheme.entity.ZookeeperEntity;
import com.lynn.config.service.zookeeperScheme.util.ZkPathUtil;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.KeeperException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright @ 2013QIANLONG. All right reserved. Class Name : com.lynn.zkc
 * Description : zookeeper树形结构数据、目录操作基础类 Author : cailinfeng Date : 2016/1/19
 */
public class TreeCacheTemplate {

	public static void start(TreeCache treeCache) throws Exception {
		treeCache.start();
	}

	public static void start(TreeCache treeCache, TreeCacheListener listener) throws Exception {
		treeCache.start();
		addListener(treeCache, listener);
	}

	/**
	 * 方法 addListener 功能描述 ：为cache添加监听器
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/22
	 * @param
	 * @return
	 *
	 */
	public static void addListener(TreeCache cache, TreeCacheListener listener) {
		cache.getListenable().addListener(listener);
	}

	/**
	 * 方法 getListForTree 功能描述 ：获取树形结构的数据
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/22
	 * @param
	 * @return
	 *
	 */
	public static ZkTree<ZookeeperEntity> getListForTree(CuratorFramework client, TreeCache cache, String path) {
		try {
			// 创建zookeeper文件夹树
			ZkTree<ZookeeperEntity> tree = new ZkTree<>();
			// 创建根结点、添加根结点到树
			ZookeeperEntity rootNode = new ZookeeperEntity(client.getNamespace(), null);
			tree.addNode(null, rootNode);
			getChildrenDataForTree(client, path, tree, rootNode);
			return tree;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 方法 getListForMap 功能描述 ：获取map结构的数据
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/22
	 * @param
	 * @return
	 *
	 */
	public static Map<String, String> getListForMap(CuratorFramework client, TreeCache cache, String path) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			path = ZkPathUtil.checkAndSetPathBeginwithSlash(path);
			// 放入当前节点的数据
			resultMap.put(path, new String(client.getData().forPath(path), "UTF-8"));
			getChildrenDataForMap(client, path, resultMap);
			return resultMap;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 方法 removeNode 功能描述 ：删除结点
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/22
	 * @param
	 * @return
	 *
	 */
	public static void removeNode(CuratorFramework client, String path) throws Exception {
		String nodePath = ZkPathUtil.makeNodePath(path);
		try {
			client.delete().guaranteed().deletingChildrenIfNeeded().forPath(nodePath);
		} catch (KeeperException.NoNodeException e) {
			// ignore
		}
	}

	/**
	 * 
	 * Description:删除所有节点
	 * 
	 * @param client
	 * @throws Exception
	 * @author xiongweitao
	 * @date 2016年3月10日 下午5:14:05
	 */
	public static void clean(CuratorFramework client) throws Exception {
		try {
			List<String> list = client.getChildren().forPath("/");
			for (String sonPath : list) {
				// "/zookeeper/quota"是zk默认节点,不可以删除
				if (sonPath.contains("zookeeper")) {
					continue;
				}
				String nodePath = ZkPathUtil.makeNodePath(sonPath);
				client.delete().guaranteed().deletingChildrenIfNeeded().forPath(nodePath);
			}
		} catch (KeeperException.NoNodeException e) {
			// ignore
		}
	}

	/**
	 * 方法 setValue 功能描述 ：修改节点数据,如果节点不存在，则创建节点
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/22
	 * @param client
	 *            path 对应namespace下的全路径 格式 /jsonProperties/data value
	 * @return
	 *
	 */
	public static void setOrCreateValue(CuratorFramework client, String path, String value) throws Exception {
		String nodePath = ZkPathUtil.makeNodePath(path);
		byte[] bytes = value.getBytes();
		try {
			client.setData().forPath(nodePath, bytes);
		} catch (KeeperException.NoNodeException e) {
			client.create().creatingParentsIfNeeded().forPath(nodePath, bytes);
		}
	}

	/**
	 * 方法 removeListener 功能描述 ：移除Cache中的监听器
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/22
	 * @param
	 * @return
	 *
	 */
	public void removeListener(TreeCache cache, TreeCacheListener listener) {
		cache.getListenable().removeListener(listener);
	}

	private static void getChildrenDataForTree(CuratorFramework client, String path, ZkTree<ZookeeperEntity> tree, ZookeeperEntity zookeeperNode) {
		// path should be start with /

		List<String> list = new ArrayList<>();
		try {
			String tempPath = ZkPathUtil.checkAndSetPathBeginwithSlash(path);
			list = client.getChildren().forPath(tempPath);
			if (list != null) {
				if (list.size() > 0) {
					for (String childNode : list) {
						String nextPath = "";
						if (tempPath.length() == 1) {
							nextPath = "/" + childNode;
						} else {
							nextPath = tempPath + "/" + childNode;
						}
						ZookeeperEntity data = new ZookeeperEntity("/" + childNode, new String(client.getData().forPath(nextPath), "UTF-8"));
						tree.addNode(tree.getNode(zookeeperNode), data);
						getChildrenDataForTree(client, nextPath, tree, data);
					}
				} else {
					tree.addNode(tree.getNode(zookeeperNode), new ZookeeperEntity(tempPath, new String(client.getData().forPath(tempPath), "UTF-8")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getChildrenDataForMap(CuratorFramework client, String path, Map<String, String> result) {
		// path should be start with /
		List<String> list = new ArrayList<>();
		try {
			String tempPath = ZkPathUtil.checkAndSetPathBeginwithSlash(path);
			list = client.getChildren().forPath(tempPath);
			if (list != null) {
				if (list.size() > 0) {
					for (String childNode : list) {
						String nextPath = "";
						if (tempPath.length() == 1) {
							nextPath = "/" + childNode;
						} else {
							nextPath = tempPath + "/" + childNode;
						}
						result.put(nextPath, new String(client.getData().forPath(nextPath), "UTF-8"));
						getChildrenDataForMap(client, nextPath, result);
					}
				} else {
					// result.put(tempPath,new
					// String(client.getData().forPath(tempPath),"UTF-8"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
