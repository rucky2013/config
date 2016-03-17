/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.manager.manager.impl;

import com.mobanker.config.api.entity.Menus;
import com.mobanker.config.api.manager.MenusManager;
import com.mobanker.config.manager.service.MenusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.tkj.cs.back.manager.impl
 * @ClassName:MenusManagerImpl
 * @author xiongweitao
 * @date 2015年12月30日 下午1:48:54
 */
@Service
public class MenusManagerImpl implements MenusManager {

    @Resource
    private MenusService menusService;

    @Override
    public List<Menus> getAllMenus() {
        return menusService.getAllMenus();
    }
}
