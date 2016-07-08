package com.lynn.config.manager.manager.impl;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.SystemServerIpDownLog;
import com.lynn.config.api.manager.SystemServerIpDownLogManager;
import com.lynn.config.service.service.SystemServerIpDownLogService;
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
