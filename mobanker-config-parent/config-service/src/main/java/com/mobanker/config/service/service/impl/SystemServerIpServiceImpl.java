package com.mobanker.config.service.service.impl;
import com.github.pagehelper.PageHelper;
import com.mobanker.config.service.dao.SystemServerIpDao;
import com.mobanker.config.api.dto.PageInfo;
import com.mobanker.config.api.dto.Pagenation;
import com.mobanker.config.service.service.SystemServerIpService;
import org.springframework.stereotype.Service;
import com.mobanker.config.api.entity.SystemServerIp;
import com.mobanker.framework.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.service.impl
 * @ClassName:ServerIpServiceImpl
 * @author xiongweitao
 * @date 2016年1月20日 下午4:08:55
 */
@Service
public class SystemServerIpServiceImpl extends BaseServiceImpl<SystemServerIp> implements SystemServerIpService {
    @Resource
    private SystemServerIpDao systemServerIpDao;
    @Override
    public PageInfo<SystemServerIp> getSystemServerIpByCondition(SystemServerIp systemServerIp, Pagenation pagenation) {
        PageHelper.startPage(pagenation.getPageNum(), pagenation.getPageSize());
        List<SystemServerIp> list = systemServerIpDao.getSystemServerIpByCondition(systemServerIp);
        PageInfo<SystemServerIp> page = new PageInfo<SystemServerIp>(list);
        return page;
    }

    @Override
    public List<String> getgetSystemName() {
        return systemServerIpDao.getSystemName();
    }
}
