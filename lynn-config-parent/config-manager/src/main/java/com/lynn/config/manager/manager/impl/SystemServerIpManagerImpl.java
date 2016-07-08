package com.lynn.config.manager.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.lynn.common.utils.HttpClientUtils;
import com.lynn.config.api.constants.CommonConstants;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.api.entity.SystemServerIp;
import com.lynn.config.api.entity.SystemServerIpDownLog;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.SystemServerIpManager;
import com.lynn.config.service.service.ActiveSystemVariableService;
import com.lynn.config.service.service.SystemServerIpDownLogService;
import com.lynn.config.service.service.SystemServerIpService;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.framework.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fancongchun on 2016/1/22.
 */
@Service
@Transactional(readOnly = true)
public class SystemServerIpManagerImpl implements SystemServerIpManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemServerIpManagerImpl.class);

	@Resource
	private SystemServerIpService systemServerIpService;
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Resource
	private ActiveSystemVariableService activeSystemVariableService;
	@Resource
	private SystemServerIpDownLogService systemServerIpDownLogService;

	@Override
	public PageInfo<SystemServerIp> getSystemServerIpByCondition(SystemServerIp systemServerIp, Pagenation pagenation) {
		return systemServerIpService.getSystemServerIpByCondition(systemServerIp, pagenation);
	}

	@Override
	public List<String> getgetSystemName() {
		return systemServerIpService.getgetSystemName();
	}

	@Override
	@Transactional(readOnly = false)
	public void addOrUpdateSystemVariable(SystemServerIp systemServerIp) {
		SystemServerIp findParam = null;
		if (ValidateUtil.isNullOrEmpty(systemServerIp.getId())) {
			findParam = new SystemServerIp();
			findParam.setSystemName(systemServerIp.getSystemName());
			findParam.setIp(systemServerIp.getIp());
			List<SystemServerIp> list = systemServerIpService.getByObj(findParam);
			if (ValidateUtil.isNullOrEmpty(list)) {
				systemServerIp.setStatus(DataStatus.NORMAL);
				systemServerIpService.insert(systemServerIp);
			} else {
				throw new CommonException(ExceptionConstants.Server.SYSTEM_IP_EXISTS, ExceptionConstants.Server.SYSTEM_IP_EXISTS_MSG);
			}
		} else {
			SystemServerIp ss = systemServerIpService.getById(systemServerIp.getId());
			if (ValidateUtil.isNullOrEmpty(ss)) {
				throw new CommonException(ExceptionConstants.System.ID_NOT_EXISTS, ExceptionConstants.System.ID_NOT_EXISTS_MSG);
			}
			if(ValidateUtil.isNullOrEmpty(systemServerIp.getStatus())){//修改，删除直接更新状态
				findParam = new SystemServerIp();
				findParam.setSystemName(systemServerIp.getSystemName());
				findParam.setIp(systemServerIp.getIp());
				List<SystemServerIp> list = systemServerIpService.getByObj(findParam);
				if (!ValidateUtil.isNullOrEmpty(list)) {
					throw new CommonException(ExceptionConstants.Server.SYSTEM_IP_UPDATE_EXISTS, ExceptionConstants.Server.SYSTEM_IP_UPDATE_EXISTS_MSG);
				}
			}
			systemServerIpService.update(systemServerIp);
		}
	}

	@Override
	public void checkAllServer(final String ipAddress) {
		// 异步执行检测所有服务器
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					LOGGER.debug("异步执行检测所有的服务器状态");
					// 获取请求路径url的后缀,例如:/test/checkServer
					ActiveSystemVariable params = new ActiveSystemVariable();
					params.setSystemName(CommonConstants.SYSTEM_NAME);
					params.setNid(CommonConstants.ActiveSystemVariableNid.SERVER_CHECK_URL_SUFFIX);
					params.setStatus(DataStatus.NORMAL);
					List<ActiveSystemVariable> la = activeSystemVariableService.getByObj(params);
					if (ValidateUtil.isNullOrEmpty(la)) {
						throw new CommonException(ExceptionConstants.System.SYSTEMVARIABLE_ERROR, ExceptionConstants.System.SYSTEMVARIABLE_ERROR_MSG);
					}
					// 获取所有状态为NORMAL的服务器
					SystemServerIp findParams = new SystemServerIp();
					findParams.setStatus(DataStatus.NORMAL);
					List<SystemServerIp> list = systemServerIpService.getByObj(findParams);
					for (SystemServerIp systemServerIp : list) {
						// 拼接字符串,获取url
						String url = CommonConstants.HTTP_PREFIX + systemServerIp.getIp() + "/" + systemServerIp.getSystemName().toLowerCase()
								+ la.get(0).getValue();
						LOGGER.info("检测服务器状态,检测连接为:" + url);
						boolean flag = false;
						try {
							String result = HttpClientUtils.doGet(url, null);
							LOGGER.info("检测服务器状态成功,检测连接为:" + url + ",返回数据为:" + result);
							JSONObject jsonObject = JSONObject.parseObject(result);
							// 返回的status为1,调用成功,服务器正常
							if (Constants.System.OK.equals((String) jsonObject.get("status"))) {
								flag = true;
							}
						} catch (Exception e) {
							LOGGER.error("检测服务器状态失败,检测连接为:" + url, e);
							flag = false;
						}
						// 服务器异常,记录服务器宕机表
						if (!flag) {
							SystemServerIpDownLog systemServerIpDownLog = new SystemServerIpDownLog();
							systemServerIpDownLog.setIp(systemServerIp.getIp());
							systemServerIpDownLog.setRemoteIp(ipAddress);
							systemServerIpDownLog.setSystemName(systemServerIp.getSystemName());
							systemServerIpDownLogService.insert(systemServerIpDownLog);
						}
					}
				} catch (Exception e) {
					LOGGER.error("异步执行检测所有的服务器状态失败", e);
				}
			}
		});
	}
}
