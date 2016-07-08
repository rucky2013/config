package com.lynn.config.service.zookeeperScheme.entity;

import lombok.Data;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.entity
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/22
 */
@Data
public class ZookeeperEntity {

    private String path;

    private Object data;

    public ZookeeperEntity(String path,Object data){
        this.path = path;
        this.data = data;
    }
}
