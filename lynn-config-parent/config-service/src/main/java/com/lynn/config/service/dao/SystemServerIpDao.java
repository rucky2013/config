/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.dao;

import com.lynn.config.api.entity.SystemServerIp;
import com.lynn.framework.dao.BaseDao;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.dao
 * @ClassName:ServerIpDao
 * @author xiongweitao
 * @date 2016年1月20日 下午4:07:30
 */
public interface SystemServerIpDao extends BaseDao<SystemServerIp> {
    public List<SystemServerIp> getSystemServerIpByCondition(SystemServerIp serverIp);
    public List<String> getSystemName();
}
