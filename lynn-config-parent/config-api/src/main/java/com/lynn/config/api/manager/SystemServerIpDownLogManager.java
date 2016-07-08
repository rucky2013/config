package com.lynn.config.api.manager;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.SystemServerIpDownLog;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpDownLogManager {
    PageInfo<SystemServerIpDownLog> getSystemServerIpDownLogByCondition(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation);
}
