package com.mobanker.config.bsw.manager.impl;

import com.mobanker.config.bsw.manager.StaticSystemVariableManager;
import com.mobanker.config.bsw.zookeeperScheme.factory.ZookeeperFactory;
import com.mobanker.config.bsw.zookeeperScheme.template.TreeCacheTemplate;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.mobanker.config.bsw.manager.impl
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/26
 */
public class StaticSystemVariableManagerImplTest extends BaseTest{

    @Resource
    private StaticSystemVariableManager staticSystemVariableManager;

    @Test
    public void testPushTreeDataToZk() throws Exception {
        staticSystemVariableManager.pushTreeDataToZk(null);
    }

    @Test
    public void testCreateChild(){

        CuratorFramework client = ZookeeperFactory.get(staticSystemVariableManager.getZkConfigBySystem(null));
        try {
            TreeCacheTemplate.setOrCreateValue(client,"/root/financial","");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}