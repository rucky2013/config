package com.lynn.config.api.constants;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.config.bsw.constants
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/27
 */
public interface ZkConstants {


    public static final String KEY_ZK_SESSIONTIMEOUT = "zk.sessionTimeout";
    public static final String KEY_ZK_CONNECTIONTIMEOUT = "zk.connectionTimeout";
    public static final String KEY_ZK_RETRYTIME = "zk.disconnect.retryTime";
    public static final String KEY_ZK_CONFIGSERVER = "zk.disconnect.configServer";
    public static final String KEY_ZK_CONNECTSTRING = "zk.connectString";
    public static final String KEY_ZK_STATIC_NODE = "zk.node.static";
    public static final String KEY_ZK_ACTIV_NODE = "zk.node.active";

    /**
     * 不进行zk数据推送的系统
     */
    public static final String SYSTEM_EXCLUDE_BACKSTAGE="backstage";



}
