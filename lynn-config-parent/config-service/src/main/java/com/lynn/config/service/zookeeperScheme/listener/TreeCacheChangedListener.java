package com.lynn.config.service.zookeeperScheme.listener;

import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.utils.ValidateUtil;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.listener
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/21
 */
public class TreeCacheChangedListener implements TreeCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(TreeCacheChangedListener.class);

    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        String data = "";
        ChildData childData = event.getData();
        if (childData == null) {
            return;
        }
        if( !ValidateUtil.isNullOrEmpty(childData.getData())){
            data = new String(childData.getData());
        }
        String dataPath = ZKPaths.getNodeFromPath(childData.getPath());
        switch (event.getType()) {
            case NODE_ADDED: {
                System.out.println("TreeNode added: " + dataPath + ", value: "
                        + data);
                updateActiveMapData(dataPath,data);
                break;
            }
            case NODE_UPDATED: {
                System.out.println("TreeNode changed: " + dataPath + ", value: "
                        + data);
                updateActiveMapData(dataPath,data);
                break;
            }
            case NODE_REMOVED: {
                System.out.println("TreeNode removed: " + dataPath);
                removeActiveMapDataByDatapath(dataPath);
                break;
            }
            default:
                System.out.println("Other event: " + event.getType().name());
        }
    }

    /**
    * 方法 addOrUpdateActiveMapData 功能描述 ：
    * @author cailinfeng
    * @createTime 2016/1/25
    * @param 
    * @return 
    *
    */
    private void updateActiveMapData(String dataPath,String data){
        logger.info("ZOOKEEPER 动态参数节点{}修改,同步至此项目", dataPath);
        try {
            if (!ValidateUtil.isNullOrEmpty(data)) {
                @SuppressWarnings("unchecked")
				Map<String, Object> zMap = JSONObject.parseObject(data, Map.class);
//                CacheManager.getActiveMap().put(dataPath, zMap);
                logger.debug("ZOOKEEPER 动态参数节点{}修改,修改后内容为{}", dataPath, JSONObject.toJSONString(zMap));
            }
        } catch (Exception e) {
            logger.error("ZOOKEEPER更新缓存数据失败", e);
        }
    }

    /**
    * 方法 removeActiveMapDataWithDatapath 功能描述 ：
    * @author cailinfeng
    * @createTime 2016/1/25
    * @param
    * @return
    *
    */
    private void removeActiveMapDataByDatapath(String dataPath){
        logger.info("ZOOKEEPER 动态参数节点{}删除，同步修改本地缓存", dataPath);
        try {
            if (!ValidateUtil.isNullOrEmpty(dataPath)) {
//                CacheManager.getActiveMap().remove(dataPath);
//                logger.debug("ZOOKEEPER 动态参数节点{}删除，删除后内容为{}", dataPath, JSONObject.toJSONString(CacheManager.getActiveMap()));
            }
        } catch (Exception e) {
            logger.error("ZOOKEEPER更新缓存数据失败", e);
        }
    }
}
