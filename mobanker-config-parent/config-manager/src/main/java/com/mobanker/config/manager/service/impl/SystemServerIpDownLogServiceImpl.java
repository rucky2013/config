package com.mobanker.config.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.mobanker.config.api.dto.PageInfo;
import com.mobanker.config.api.dto.Pagenation;
import com.mobanker.config.manager.dao.SystemServerIpDownLogDao;
import com.mobanker.framework.service.impl.BaseServiceImpl;
import com.mobanker.config.api.entity.SystemServerIpDownLog;
import com.mobanker.config.manager.service.SystemServerIpDownLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fancongchun on 2016/1/22.
 */
@Service
public class SystemServerIpDownLogServiceImpl extends BaseServiceImpl<SystemServerIpDownLog> implements SystemServerIpDownLogService {
    @Resource
    private SystemServerIpDownLogDao systemServerIpDownLogDao;
    @Override
    public PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation) {
        PageHelper.startPage(pagenation.getPageNum(), pagenation.getPageSize());
        List<SystemServerIpDownLog> list = systemServerIpDownLogDao.getSystemServerIpDownLogByCondition(systemServerIpDownLog);
        PageInfo<SystemServerIpDownLog> page = new PageInfo<SystemServerIpDownLog>(list);
        return page;
    }
}
