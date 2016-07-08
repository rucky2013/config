/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.service.dao.ActiveSystemVariableDao;
import com.lynn.config.service.service.ActiveSystemVariableService;
import com.lynn.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service.impl
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
