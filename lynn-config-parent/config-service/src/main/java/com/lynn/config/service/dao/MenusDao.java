/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.dao;

import com.lynn.config.api.entity.Menus;
import com.lynn.framework.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.dao
 * @ClassName:MenusDao
 * @author xiongweitao
 * @date 2016年1月20日 下午3:31:52
 */
public interface MenusDao extends BaseDao<Menus> {
    List<Menus> getMenusByParentMenuId(Map<String, String> params);

    List<Menus> getTopMenu(Menus menu);
}
