package com.mobanker.config.service.manager;

import com.mobanker.config.service.dto.PageInfo;
import com.mobanker.config.service.dto.Pagenation;
import com.mobanker.config.service.entity.SystemServerIpDownLog;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogManager {
    PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation);
}
