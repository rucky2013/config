package com.lynn.config.service.zookeeperScheme;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.service.zookeeperScheme.entity.ZookeeperConfig;
import com.lynn.framework.spring.property.PropertyPlaceholderConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.bg.zookeeper
 * Description : 静态资源持有者
 * Author : cailinfeng
 * Date : 2016/1/15
 */
@SuppressWarnings("all")
public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{

    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperPropertyPlaceholderConfigurer.class);


    private static ZookeeperConfig zookeeperConfig;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        Map staticMapFromZk = null;
        try {
            //1、用读取到zookeeper的配置，设置zookeeperConfig
            try {
                zookeeperConfig = ZookeeperConfig.setZookeeperConfig(props);
            } catch (Exception e) {
                logger.error("setZookeeperConfig error,check you file application.properties and ZookeeperConfig whether matched, param-->{}",props,e);
            }
            //2、从zookeeper中填充系统相关的例如jdbc、redis、mongoDB等静态配置
            staticMapFromZk = setStaticSystemVariableFromZk(props);
        }catch (UnsupportedEncodingException e) {
            logger.error("resolve data with UTF-8 failed",e);
        }catch (Exception e){
            logger.error("fillProperties failed",e);
        }
        //如果从zookeeper中获取静态变量时发生异常,则从配置服务中获取
        if (staticMapFromZk == null) {
            setStaticSystemVariableFromConfigServer(props);
        }
        //3、再次调用父类方法，执行最终的bean填充
        super.processProperties(beanFactoryToProcess, props);
        logger.debug("--------------->  填充静态参数Propertis成功，data{}",props);
        //设置动态变量
        Map resultFromZk = null;
        try {
            resultFromZk = setActiveSystemVariableFromZk();
        } catch (Exception e) {
            logger.error("setActiveSystemVariable error",e);
        }
        //如果从zk中设置失败，启动timer去尝试获取，直至获取成功
        if (resultFromZk == null) {
            setTimerTaskOfSetActiveSystemVariableUtilSuccess();
            setActiveSystemVariableFromConfigServer();
        }
    }


    /**
    * 方法 setSystemStaticProperties 功能描述 ：设置系统相关的静态变量
    * @author cailinfeng
    * @createTime 2016/1/19
    * @param 
    * @return 
    *
    */
    private Map setStaticSystemVariableFromZk(Properties props) throws Exception {
//        TreeCache treeCache = ZooKeeperTreeCacheFactory.getTreeCache(zookeeperConfig,zookeeperConfig.getStaticConfigPath());
//        Map map = TreeCacheTemplate.getListForMap(
//                ZookeeperFactory.get(zookeeperConfig), treeCache, zookeeperConfig.getStaticConfigPath());
//        try{
//            if (map != null) {
//                map.remove(zookeeperConfig.getStaticConfigPath());
//                Set<Map.Entry<String,String>> dynamicSysVarMap = map.entrySet();
//                String jsonValueFromZookeeper = "";
//                String jsonKeyFromZookeeper = "";
//                Map<String,Object> variableMapOfModule = new HashMap<>();
//                for( Map.Entry m : dynamicSysVarMap){
//                    jsonKeyFromZookeeper = m.getKey().toString();
//                    jsonValueFromZookeeper = m.getValue().toString();
//                    try {
//                        variableMapOfModule = JSONObject.parseObject(jsonValueFromZookeeper,Map.class);
//                        //设置CacheManager.StaticMap
//                        CacheManager.getStaticMap().put(jsonKeyFromZookeeper.substring(jsonKeyFromZookeeper.lastIndexOf("/")+1),variableMapOfModule);
//                        if (variableMapOfModule != null) {
//                            for(Map.Entry childEntry : variableMapOfModule.entrySet()){
//                                //设置props
//                                props.put(childEntry.getKey(),childEntry.getValue());
//                            }
//                        }
//                    } catch (Exception e) {
//                        logger.error(" setSystemStaticPropertiesFromZk() , parse object from zookeeper error , {}" ,jsonValueFromZookeeper,e);
//                    }
//                }
//                logger.debug("setSystemStaticPropertiesFromZk success , result {}" , CacheManager.getStaticMap());
//            }
//        }finally {
//            if (treeCache != null) {
//                ZooKeeperTreeCacheFactory.close(treeCache);
//            }
//            ZookeeperFactory.close(zookeeperConfig);
//        }
//        return map;
        return null;
    }

    public static Map setActiveSystemVariableFromZk(){

//        TreeCache treeCache = ZooKeeperTreeCacheFactory.getTreeCache(zookeeperConfig,zookeeperConfig.getDynamicConfigPath());
//        Map map = TreeCacheTemplate.getListForMap(
//                ZookeeperFactory.get(zookeeperConfig), treeCache, zookeeperConfig.getDynamicConfigPath());
//        if (map != null) {
//            map.remove(zookeeperConfig.getDynamicConfigPath());
//            Set<Map.Entry<String,String>> dynamicSysVarMap = map.entrySet();
//            String jsonValueFromZookeeper = "";
//            String jsonKeyFromZookeeper = "";
//            Map<String,Object> variable = new HashMap<>();
//            for( Map.Entry m : dynamicSysVarMap){
//                jsonKeyFromZookeeper = m.getKey().toString();
//                jsonValueFromZookeeper = m.getValue().toString();
//                try {
//                    variable = JSONObject.parseObject(jsonValueFromZookeeper,Map.class);
//                    CacheManager.getActiveMap().put(jsonKeyFromZookeeper.substring(jsonKeyFromZookeeper.lastIndexOf("/")+1),variable);
//                } catch (Exception e) {
//                    logger.error(" setActiveSystemVariableFromZk() , parse object from zookeeper error , {}" ,jsonValueFromZookeeper,e);
//                }
//            }
//            try {
//                TreeCacheTemplate.start(treeCache,new TreeCacheChangedListener());
//            } catch (Exception e) {
//                logger.error("add listener for zookeeper error, param{}",treeCache,e);
//            }
//            logger.debug("setActiveSystemVariableFromZk success , result {}" , CacheManager.getActiveMap());
//        }else{
//            ZooKeeperTreeCacheFactory.close(treeCache);
//            ZookeeperFactory.close(zookeeperConfig);
//        }
//        return map;
        return null;
    }

    private static void setStaticSystemVariableFromConfigServer(Properties props){
        //todo 从配置服务中获取
        String cfg = "{\"jdbc.url\":\"jdbc:mysql://192.168.1.214:3306/customer?useUnicode=true&characterEncoding=UTF-8\",\"jdbc.driver\":\"com.mysql.jdbc.Driver\",\"pool.maxPoolSize\":\"50\",\"jdbc.username\":\"root\",\"jdbc.password\":\"chengce214\",\"pool.maxWait\":\"60000\",\"pool.timeBetweenEvictionRunsMillis\":\"60000\",\"pool.minEvictableIdleTimeMillis\":\"300000\",\"pool.validationQuery\":\"SELECT1\"}";
        logger.debug("--------------->  获取静态配置参数成功，data{}",cfg);
        if (StringUtils.isNotBlank(cfg)) {
            Map<String,String> variable = JSONObject.parseObject(cfg,Map.class);
            Set<Map.Entry<String, String>> variableSet = variable.entrySet();
            logger.debug("数据---> {}",variableSet);
            for(Map.Entry entry : variableSet){
                //BeanDefinitionVisitor.resolveStringValue() 该方法中必须传入String类型的value，否则无法进行正常解析
                //即props中必须为<String,String>
                props.put(entry.getKey(), entry.getValue()+"");
            }
        }
    }

    private static void setActiveSystemVariableFromConfigServer(){
        //todo 如果resultFromZk为空，则向配置服务中获取
    }

    private static void setTimerTaskOfSetActiveSystemVariableUtilSuccess(){
        final Timer timer = new Timer();
        //设置定时任务从zk中获取动态变量直至成功
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.debug("执行定时任务,从ZK获取动态参数");
                if( setActiveSystemVariableFromZk() != null ){
                    timer.cancel();
                    logger.debug("从ZK获取动态参数成功！");
                }
            }
        }, 10000, 30000);
    }

    public static ZookeeperConfig getZookeeperConfig() {
        return zookeeperConfig;
    }
}
