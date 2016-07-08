package com.lynn.config.service.zookeeperScheme.entity;


import com.lynn.config.service.zookeeperScheme.constants.ZookeeperConstants;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.config.core.zookeeper.config
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/18
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ZookeeperConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String connection ;

    private String nameSpace ;

    private String staticConfigPath ;

    private String dynamicConfigPath ;

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getDynamicConfigPath() {
        return dynamicConfigPath;
    }

    public void setDynamicConfigPath(String dynamicConfigPath) {
        this.dynamicConfigPath = dynamicConfigPath;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getStaticConfigPath() {
        return staticConfigPath;
    }

    public void setStaticConfigPath(String staticConfigPath) {
        this.staticConfigPath = staticConfigPath;
    }

    /**
     * 方法 setZookeeperConfig 功能描述 ：通过反射、根据application.properties中读取到的
     *                                  属性props，设置zookeeperConfig
     * @author cailinfeng
     * @createTime 2016/1/19
     * @param
     * @return
     *
     */
    public static ZookeeperConfig setZookeeperConfig(Properties props) throws Exception{
        /**
         * 通过反射，遍历props，设置zookeeperConfig配置
         */
        ZookeeperConfig zookeeperConfig = new ZookeeperConfig();
		Class classType = zookeeperConfig.getClass();
        Method[] methods = classType.getMethods();

        Map<String, Method> methodMap = new HashMap<String, Method>();
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }

        Map<String, Class> typeMap = new HashMap<String, Class>();
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        for (Class<?> clazz = classType; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (!fieldMap.containsKey(field.getName())) {
                        fieldMap.put(field.getName(), field);
                        typeMap.put(field.getName(), field.getType());
                    }
                }
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz =
                // clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        for (Field field : fieldMap.values()) {
            String fieldName = field.getName();
            String fieldNameSub = null;
            if (fieldName.length() == 1) {
                fieldNameSub = fieldName.substring(0, 1).toLowerCase();
            } else {
                fieldNameSub = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1, fieldName.length());
            }
            String setMethodName = "set" + fieldNameSub;
            Method setMethod = methodMap.get(setMethodName);
            for(Object key : props.keySet()){
                if((ZookeeperConstants.prefix+fieldName).equalsIgnoreCase((String) key)){
                    setMethod.invoke(zookeeperConfig,props.get(key));
                }
            }
        }
        return zookeeperConfig;
    }


}
