/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.service.service;

import com.mobanker.config.api.entity.Menus;
import com.mobanker.framework.service.BaseService;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.service
 * @ClassName:MenusService
 * @author xiongweitao
 * @date 2016年1月20日 下午3:32:57
 */
public interface MenusService extends BaseService<Menus> {
    public List<Menus> getAllMenus();
}
