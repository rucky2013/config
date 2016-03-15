package com.mobanker.config.service.manager.impl;

import com.mobanker.config.service.dto.PageInfo;
import com.mobanker.config.service.dto.Pagenation;
import com.mobanker.config.service.entity.SystemServerIpDownLog;
import com.mobanker.config.service.manager.SystemServerIpDownLogManager;
import com.mobanker.config.service.service.SystemServerIpDownLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by fancongchun on 2016/1/22.
 */
@Service
@Transactional(readOnly = true)
public class SystemServerIpDownLogManagerImpl implements SystemServerIpDownLogManager {
    @Resource
    private SystemServerIpDownLogService systemServerIpDownLogService;

    @Override
    public PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation) {
        return systemServerIpDownLogService.getSystemServerIpDownLogByCondition(systemServerIpDownLog,pagenation);
    }
}
