/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.service.dao.StaticSystemVariableDao;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.config.service.service.StaticSystemVariableService;
import com.lynn.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.service.impl
 * @ClassName:StaticSystemVariableServiceImpl
 * @author xiongweitao
 * @date 2016年1月19日 上午10:37:23
 */
@Service
@Transactional(readOnly = true)
@Path("static")
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

	@Override
	public String getStaticStringForDubboTest() {
		return "dubbo execute without mysql access!";
	}

	@Override
	@GET
	@Path("getDbDataForDubboTest")
	@Produces({MediaType.APPLICATION_JSON})
	public List<StaticSystemVariable> getDbDataForDubboTest() {
		List result = null;
		try {
			result = staticSystemVariableDao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		StaticSystemVariable staticSystemVariable = new StaticSystemVariable();
		staticSystemVariable.setSystemName("test11");
		staticSystemVariable.setStatus(DataStatus.NORMAL);
		List<StaticSystemVariable> staticSystemVariables = staticSystemVariableDao.getStaticSystemVariableBySystemNameAndStatus(staticSystemVariable);
		return staticSystemVariables;
	}
}
