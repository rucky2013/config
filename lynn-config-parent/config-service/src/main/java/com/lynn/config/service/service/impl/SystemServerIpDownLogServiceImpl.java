package com.lynn.config.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.service.dao.SystemServerIpDownLogDao;
import com.lynn.framework.service.impl.BaseServiceImpl;
import com.lynn.config.api.entity.SystemServerIpDownLog;
import com.lynn.config.service.service.SystemServerIpDownLogService;
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
