package com.lynn.config.service.zookeeperScheme.entity;

import lombok.ToString;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.entity
 * Description : zookeeper文件夹树
 * Author : cailinfeng
 * Date : 2016/1/22
 */

@ToString(callSuper = true)
@SuppressWarnings("all")
public class ZkTree<T> {
    public ZkTreeNode<T> root;

    public ZkTree() {
    }

    public void addNode(ZkTreeNode<T> node, T newNode) {
        //增加根节点
        if (null == node) {
            if (null == root) {
                root = new ZkTreeNode(newNode);
            }
        } else {
            ZkTreeNode<T> temp = new ZkTreeNode(newNode);
            node.childList.add(temp);
        }
    }

    /*    查找newNode这个节点 */
    public ZkTreeNode<T> search(ZkTreeNode<T> input, T newNode) {

        ZkTreeNode<T> temp = null;

        if (input.node.equals(newNode)) {
            return input;
        }

        for (int i = 0; i < input.childList.size(); i++) {

            temp = search(input.childList.get(i), newNode);
            if (null != temp) {
                break;
            }
        }

        return temp;
    }

    public ZkTreeNode<T> getNode(T newNode) {
        return search(root, newNode);
    }

    public void showNode(ZkTreeNode<T> node) {
        if (null != node) {
            //循环遍历node的节点
            System.out.println(node.node.toString());

            for (int i = 0; i < node.childList.size(); i++) {
                showNode(node.childList.get(i));
            }
        }
    }
}
