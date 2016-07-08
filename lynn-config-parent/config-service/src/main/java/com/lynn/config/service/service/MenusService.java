/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service;

import com.lynn.config.api.entity.Menus;
import com.lynn.framework.service.BaseService;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service
 * @ClassName:MenusService
 * @author xiongweitao
 * @date 2016年1月20日 下午3:32:57
 */
public interface MenusService extends BaseService<Menus> {
    public List<Menus> getAllMenus();
}
