/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.serivce.dao;

import com.mobanker.config.api.entity.SystemServerIp;
import com.mobanker.framework.dao.BaseDao;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.dao
 * @ClassName:ServerIpDao
 * @author xiongweitao
 * @date 2016年1月20日 下午4:07:30
 */
public interface SystemServerIpDao extends BaseDao<SystemServerIp> {
    public List<SystemServerIp> getSystemServerIpByCondition(SystemServerIp serverIp);
    public List<String> getSystemName();
}
