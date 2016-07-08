/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service.impl;

import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.service.dao.MenusDao;
import com.lynn.config.service.service.MenusService;
import org.springframework.stereotype.Service;

import com.lynn.config.api.entity.Menus;
import com.lynn.framework.service.impl.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service.impl
 * @ClassName:MenusServiceImpl
 * @author xiongweitao
 * @date 2016年1月20日 下午3:33:22
 */
@Service
public class MenusServiceImpl extends BaseServiceImpl<Menus> implements MenusService {

    @Resource
    private MenusDao menusDao;

    @Override
    public List<Menus> getAllMenus() {
        Menus menu = new Menus();
        menu.setStatus(DataStatus.NORMAL);
        List<Menus> menus = menusDao.getTopMenu(menu);
        for( Menus m: menus ){
            getSubMenus( m );
        }
        return menus;
    }

    private void getSubMenus(Menus menu) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("menuId", menu.getMenuId()+"");
        List<Menus> subMenus = menusDao.getMenusByParentMenuId( params );
        if( subMenus != null && subMenus.size() > 0 ){
            menu.setSubMenus(subMenus);
            for( Menus tempMenu: subMenus ){
                getSubMenus( tempMenu );
            }
        }
    }
}
