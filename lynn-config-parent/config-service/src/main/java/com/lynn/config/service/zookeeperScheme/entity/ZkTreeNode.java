package com.lynn.config.service.zookeeperScheme.entity;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.entity
 * Description : zookeeper树结点
 * Author : cailinfeng
 * Date : 2016/1/22
 */

@ToString(callSuper = true)
public class ZkTreeNode<T> {

    public T node;

    private ZkTreeNode<T> parent;

    public List<ZkTreeNode<T>> childList;

    public ZkTreeNode(T t){
        node = t;
        parent = null;
        childList = new ArrayList<>();
    }

    public ZkTreeNode<T> getParent() {
        return parent;
    }
}
