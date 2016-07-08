package com.lynn.config.service.zookeeperScheme.factory;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.factory
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/20
 */
@Component
public class ZooKeeperTreeCacheFactory {

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ZooKeeperTreeCacheFactory.class);


    public static CuratorFramework getClient(Map<String,String> zookeeperConfig) {
        return ZookeeperFactory.get(zookeeperConfig);
    }

//    public TreeCache getDynamicCache(ZookeeperConfig zookeeperConfig){
//        return new TreeCache(getClient(zookeeperConfig),zookeeperConfig.getDynamicConfigPath());
//    }
//
    public static TreeCache getTreeCache(Map<String,String> zookeeperConfig, String module){
        return  new TreeCache(getClient(zookeeperConfig),module);
    }

    public static void close(TreeCache treeCache){
        CloseableUtils.closeQuietly(treeCache);
    }
}
