/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.service.service;

import com.mobanker.config.service.dto.PageInfo;
import com.mobanker.config.service.dto.Pagenation;
import com.mobanker.config.service.entity.SystemServerIp;
import com.mobanker.framework.service.BaseService;

import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.service
 * @ClassName:ServerIpService
 * @author xiongweitao
 * @date 2016年1月20日 下午4:08:27
 */
public interface SystemServerIpService extends BaseService<SystemServerIp> {
    public PageInfo<SystemServerIp> getSystemServerIpByCondition(SystemServerIp systemServerIp, Pagenation pagenation);
    public List<String> getgetSystemName();
}
