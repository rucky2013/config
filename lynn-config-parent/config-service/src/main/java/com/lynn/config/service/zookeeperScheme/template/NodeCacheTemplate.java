package com.lynn.config.service.zookeeperScheme.template;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.KeeperException;
import org.springframework.stereotype.Component;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/19
 */
@Component
public class NodeCacheTemplate {

    public void start(NodeCache nodeCache) throws Exception {
        nodeCache.start();
    }

    public void start(NodeCache nodeCache,NodeCacheListener listener) throws Exception {
        nodeCache.start();
        addListener(nodeCache,listener);
    }

    public void addListener( NodeCache cache ,NodeCacheListener listener) {
        cache.getListenable().addListener(listener);
    }

    public void removeListener( NodeCache cache ,NodeCacheListener listener) {
        cache.getListenable().removeListener(listener);
    }

    public void clearListener( NodeCache cache ) {
        cache.getListenable().clear();
    }

    public String show(CuratorFramework client,String path) {
        try {
            return new String(client.getData().forPath( path));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (cache.getCurrentData() != null){
//            return new String(cache.getCurrentData().getData());
//        }
        return null;
    }

//    public static void remove(CuratorFramework client) throws Exception {
//        try {
//            client.delete().forPath(PATH);
//        } catch (KeeperException.NoNodeException e) {
//            // ignore
//        }
//    }

    public void setValue(CuratorFramework client, String path,String value) throws Exception {
        byte[] bytes = value.getBytes();
        try {
            client.setData().forPath(path, bytes);
        } catch (KeeperException.NoNodeException e) {
            client.create().creatingParentsIfNeeded().forPath(path, bytes);
        }
    }


}
