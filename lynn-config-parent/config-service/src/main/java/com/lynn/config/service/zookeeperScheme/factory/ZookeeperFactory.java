package com.lynn.config.service.zookeeperScheme.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

import com.lynn.config.service.zookeeperScheme.entity.ZookeeperConfig;
import com.lynn.config.api.constants.ZkConstants;
import com.lynn.config.api.utils.ValidateUtil;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.config.core.factory
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/15
 */
public class ZookeeperFactory {

    public static final int MAX_RETRIES = 3;

    public static final int BASE_SLEEP_TIMEMS = 1000;

    public static final int MAX_ELAPSED_TIMEMS = 1000;

    public static final int SLEEP_MS_BETWEEN_RETRIES=50;

    public static Map<String,CuratorFramework> clientPool = new HashMap<>();

    public static CuratorFramework getByZookeeperConfig(ZookeeperConfig zookeeperConfig){
        RetryPolicy retryUntilElapsedPolicy = new RetryUntilElapsed(MAX_ELAPSED_TIMEMS,SLEEP_MS_BETWEEN_RETRIES);
        if(clientPool.get(zookeeperConfig.getNameSpace()) == null){
            CuratorFramework client = CuratorFrameworkFactory.builder()
                    .connectString(zookeeperConfig.getConnection())
                    .retryPolicy(retryUntilElapsedPolicy)
                    .namespace(zookeeperConfig.getNameSpace()).connectionTimeoutMs(5000)
                    .build();
            client.start();
            clientPool.put(zookeeperConfig.getNameSpace(),client);
        }
        return clientPool.get(zookeeperConfig.getNameSpace());
    }

    public static void closeByZkConfig(ZookeeperConfig zookeeperConfig){
        CuratorFramework client = clientPool.get(zookeeperConfig.getNameSpace());
        if (client != null) {
            client.close();
            clientPool.remove(zookeeperConfig.getNameSpace());
        }
    }

    public static void close(){
        CuratorFramework client = clientPool.get("");
        if (client != null) {
            client.close();
            clientPool.remove("");
        }
    }

    public static CuratorFramework get(Map<String,String> zookeeperConfigMap){
        RetryPolicy retryUntilElapsedPolicy = new RetryUntilElapsed(MAX_ELAPSED_TIMEMS,SLEEP_MS_BETWEEN_RETRIES);
        String connection = zookeeperConfigMap.get(ZkConstants.KEY_ZK_CONNECTSTRING);
        if(ValidateUtil.isNullOrEmpty(connection)){
            return null;
        }
        Integer timeOutMs = 30000;
        if(!ValidateUtil.isNullOrEmpty(zookeeperConfigMap.get(ZkConstants.KEY_ZK_CONNECTIONTIMEOUT))){
            timeOutMs = Integer.valueOf(zookeeperConfigMap.get(ZkConstants.KEY_ZK_CONNECTIONTIMEOUT));
        }
        if(clientPool.get("") == null){
            CuratorFramework client = CuratorFrameworkFactory.builder()
                    .connectString(connection)
                    .retryPolicy(retryUntilElapsedPolicy)
                    .connectionTimeoutMs(timeOutMs)
                    .build();
            client.start();
            clientPool.put("",client);
        }
        return clientPool.get("");
    }
}
