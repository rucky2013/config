package com.mobanker.config.bsw.manager.impl;

import com.mobanker.config.bsw.manager.StaticSystemVariableManager;
import com.mobanker.config.bsw.manager.ZkDataPushManager;
import com.mobanker.config.service.constants.enums.DataStatus;
import com.mobanker.config.service.constants.enums.ModuleStatus;
import com.mobanker.config.service.entity.SystemModule;
import com.mobanker.config.service.service.StaticSystemVariableService;
import com.mobanker.config.service.service.SystemModuleService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.mobanker.config.bsw.manager.impl
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/29
 */
public class ZkDataPushManagerImplTest extends BaseTest{

    @Resource
    private StaticSystemVariableManager staticSystemVariableManager;

    @Resource
    private StaticSystemVariableService staticSystemVariableService;

    @Resource
    private ZkDataPushManager zkDataPushManager;
    
    @Resource
    private SystemModuleService systemModuleService;

    @Test
    public void testPushListToZk() throws Exception {
        zkDataPushManager.pushListToZk(staticSystemVariableService.getAll());
    }

    @Test
    public void testName() throws Exception {
        SystemModule ssy = new SystemModule();
        ssy.setModuleName("customer");
        ssy.setFlag(ModuleStatus.STATIC);
        ssy.setStatus(DataStatus.NORMAL);

//        zkDataPushManager.delSystemModuleToZk(systemModuleService.getSonSystemModuleNameByCondition(ssy));
    }
}