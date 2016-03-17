/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mobanker.config.manager.dao.ActiveSystemVariableDao;
import com.mobanker.config.manager.service.ActiveSystemVariableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.mobanker.config.api.dto.PageInfo;
import com.mobanker.config.api.dto.Pagenation;
import com.mobanker.config.api.entity.ActiveSystemVariable;
import com.mobanker.framework.service.impl.BaseServiceImpl;

/**
 * 
 * Description:
 * 
 * @PackageName:com.mobanker.config.core.service.impl
 * @ClassName:ActiveSystemVariableServiceImpl
 * @author xiongweitao
 * @date 2016年1月19日 上午10:37:29
 */
@Service
@Transactional(readOnly = true)
public class ActiveSystemVariableServiceImpl extends BaseServiceImpl<ActiveSystemVariable> implements ActiveSystemVariableService {

	@Resource
	private ActiveSystemVariableDao activeSystemVariableDao;

	@Override
	public List<ActiveSystemVariable> getActiveSystemVariableBySystemNameAndStatus(ActiveSystemVariable activeSystemVariable) {
		return activeSystemVariableDao.getActiveSystemVariableBySystemNameAndStatus(activeSystemVariable);
	}

	@Override
	public PageInfo<ActiveSystemVariable> getActiveSystemVariableByCondition(ActiveSystemVariable activeSystemVariable, Pagenation pagenation) {
		PageHelper.startPage(pagenation.getPageNum(), pagenation.getPageSize());
		List<ActiveSystemVariable> list = activeSystemVariableDao.getActiveSystemVariableByCondition(activeSystemVariable);
		PageInfo<ActiveSystemVariable> page = new PageInfo<ActiveSystemVariable>(list);
		return page;
	}

	@Override
	public void updateByModuleStatus(ActiveSystemVariable activeSystemVariable) {
		activeSystemVariableDao.updateByModuleStatus(activeSystemVariable);
	}

	@Override
	public List<ActiveSystemVariable> getZkConfigBySystem() {
		return activeSystemVariableDao.getZkConfigBySystem();
	}
}
