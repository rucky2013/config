/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.mobanker.config.service.dao.StaticSystemVariableDao;
import com.mobanker.config.service.dto.PageInfo;
import com.mobanker.config.service.dto.Pagenation;
import com.mobanker.config.service.entity.StaticSystemVariable;
import com.mobanker.config.service.service.StaticSystemVariableService;
import com.mobanker.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.service.impl
 * @ClassName:StaticSystemVariableServiceImpl
 * @author xiongweitao
 * @date 2016年1月19日 上午10:37:23
 */
@Service
@Transactional(readOnly = true)
public class StaticSystemVariableServiceImpl extends BaseServiceImpl<StaticSystemVariable> implements StaticSystemVariableService {

	@Resource
	private StaticSystemVariableDao staticSystemVariableDao;

	@Override
	public List<StaticSystemVariable> getStaticSystemVariableBySystemNameAndStatus(StaticSystemVariable staticSystemVariable) {
		return staticSystemVariableDao.getStaticSystemVariableBySystemNameAndStatus(staticSystemVariable);
	}

	@Override
	public PageInfo<StaticSystemVariable> getStaticSystemVariableByCondition(StaticSystemVariable staticSystemVariable, Pagenation pagenation) {
		PageHelper.startPage(pagenation.getPageNum(), pagenation.getPageSize());
		List<StaticSystemVariable> list = staticSystemVariableDao.getStaticSystemVariableByCondition(staticSystemVariable);
		PageInfo<StaticSystemVariable> page = new PageInfo<StaticSystemVariable>(list);
		return page;
	}

//	@Override
//	public List<StaticSystemVariable> getZkConfigBySystem() {
//		return staticSystemVariableDao.getZkConfigBySystem();
//	}

	@Override
	public void updateByModuleStatus(StaticSystemVariable staticSystemVariable) {
		staticSystemVariableDao.updateByModuleStatus(staticSystemVariable);		
	}
}
