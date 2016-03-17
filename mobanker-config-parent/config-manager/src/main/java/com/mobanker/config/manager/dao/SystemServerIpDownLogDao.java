package com.mobanker.config.manager.dao;

import com.mobanker.framework.dao.BaseDao;
import com.mobanker.config.api.entity.SystemServerIpDownLog;

import java.util.List;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogDao extends BaseDao<SystemServerIpDownLog> {
    public List<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog);
}
