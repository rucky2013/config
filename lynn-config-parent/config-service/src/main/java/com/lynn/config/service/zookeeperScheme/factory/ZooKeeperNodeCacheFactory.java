package com.lynn.config.service.zookeeperScheme.factory;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.factory
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/20
 */
public class ZooKeeperNodeCacheFactory{

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ZooKeeperNodeCacheFactory.class);

    public static CuratorFramework getClient(Map<String,String> zookeeperConfig) {
        return ZookeeperFactory.get(zookeeperConfig);
    }

    public static NodeCache getNodeCache(Map<String,String> zookeeperConfig, String path){
        return  new NodeCache(getClient(zookeeperConfig),path);
    }

}
