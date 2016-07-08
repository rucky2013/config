/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.SystemServerIp;
import com.lynn.framework.service.BaseService;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service
 * @ClassName:ServerIpService
 * @author xiongweitao
 * @date 2016年1月20日 下午4:08:27
 */
public interface SystemServerIpService extends BaseService<SystemServerIp> {
    public PageInfo<SystemServerIp> getSystemServerIpByCondition(SystemServerIp systemServerIp, Pagenation pagenation);
    public List<String> getgetSystemName();
}
