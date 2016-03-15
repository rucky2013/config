package com.mobanker.config.service.service;

import com.mobanker.config.service.dto.PageInfo;
import com.mobanker.config.service.dto.Pagenation;
import com.mobanker.framework.service.BaseService;
import com.mobanker.config.service.entity.SystemServerIpDownLog;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogService extends BaseService<SystemServerIpDownLog> {
    public PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation);
}
