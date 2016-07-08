package com.lynn.config.service.dao;

import com.lynn.framework.dao.BaseDao;
import com.lynn.config.api.entity.SystemServerIpDownLog;

import java.util.List;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogDao extends BaseDao<SystemServerIpDownLog> {
    public List<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog);
}
