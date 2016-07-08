package com.lynn.config.service.zookeeperScheme.util;

import org.apache.curator.utils.ZKPaths;

import com.lynn.config.api.utils.ValidateUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.zkc.zookeeper.util
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/22
 */
public class ZkPathUtil {

    /**
    * 方法 makeNodePath 功能描述 ：校验生成节点路径
     *                               创建、删除、修改节点时使用
    * @author cailinfeng
    * @createTime 2016/1/22
    * @param
    * @return
    *
    */
    public static String makeNodePath(String path) throws Exception{
        String nodePath = "";
        if(path.endsWith("/")){
            throw new Exception("Invalid path name " + path+" , path should not endWith '/' ");
        }
        if(!path.contains("/")){
            nodePath = ZKPaths.makePath("",path);
        }else{
            String parentPath = path.substring(0,path.lastIndexOf("/"));
            String node = path.substring(path.lastIndexOf("/")+1);
            nodePath = ZKPaths.makePath(parentPath,node);
        }
        return nodePath;
    }

    /**
    * 方法 checkAndSetPathBeginwithSlash 功能描述 ：path 必须以 / 起始，
     *                                  查询结点时使用
    * @author cailinfeng
    * @createTime 2016/1/22
    * @param
    * @return
    *
    */
    public static String checkAndSetPathBeginwithSlash(String path) throws Exception{
        if(path.endsWith("/")){
            throw new Exception("Invalid path name " + path+" , path should not endWith '/' ");
        }
        String tempPath = "";
        if(ValidateUtil.isNullOrEmpty(path)){
            tempPath = "/";
        }
        if(path.startsWith("/")){
            tempPath = path;
        }else{
            tempPath = "/"+path;
        }
        return tempPath;
    }

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "/META-INF/spring/applicationContext.xml" });
        context.start();
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }
}
