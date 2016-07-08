package com.lynn.config.service.zookeeperScheme.listener;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.listener
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/20
 */
public class NodeCacheChangedListener implements NodeCacheListener {

    private NodeCache nodeCache;

    public NodeCacheChangedListener(NodeCache nodeCache){this.nodeCache=nodeCache;}

    @Override
    public void nodeChanged() throws Exception {
        if (nodeCache.getCurrentData() != null){
            System.err.println(new String(nodeCache.getCurrentData().getData()));
        }
    }
}
