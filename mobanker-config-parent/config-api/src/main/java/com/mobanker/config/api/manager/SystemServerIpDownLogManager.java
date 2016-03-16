package com.mobanker.config.api.manager;

import com.mobanker.config.api.dto.PageInfo;
import com.mobanker.config.api.dto.Pagenation;
import com.mobanker.config.api.entity.SystemServerIpDownLog;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogManager {
    PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation);
}
