package com.lynn.config.service.service;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.framework.service.BaseService;
import com.lynn.config.api.entity.SystemServerIpDownLog;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogService extends BaseService<SystemServerIpDownLog> {
    public PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation);
}
