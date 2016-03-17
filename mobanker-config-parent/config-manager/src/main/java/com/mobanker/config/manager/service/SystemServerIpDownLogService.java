package com.mobanker.config.manager.service;

import com.mobanker.config.api.dto.PageInfo;
import com.mobanker.config.api.dto.Pagenation;
import com.mobanker.framework.service.BaseService;
import com.mobanker.config.api.entity.SystemServerIpDownLog;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogService extends BaseService<SystemServerIpDownLog> {
    public PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation);
}
