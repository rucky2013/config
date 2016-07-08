/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.bsw.web;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.SystemServerIpManager;
import com.lynn.config.api.utils.NetworkUtil;
import com.lynn.framework.constant.Constants;
import com.lynn.framework.dto.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("quartz")
public class QuartzController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzController.class);

	@Resource
	private SystemServerIpManager systemServerIpManager;

	@ResponseBody
	@RequestMapping(value = "checkAllServer", method = RequestMethod.GET)
	public ResponseEntity checkAllServer(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("定时任务,检查所有的服务器,请求IP为{}", ipAddress);
		}
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		try {
			systemServerIpManager.checkAllServer(ipAddress);
			entity.setStatus(Constants.System.OK);
			entity.setError(ExceptionConstants.System.SERVER_SUCCESS);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("定时任务,检查所有的服务器成功,返回数据为{}", JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("定时任务,检查所有的服务器失败,返回数据为{}", JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("定时任务,检查所有的服务器失败,返回数据为{}", JSONObject.toJSONString(entity), e);
		}
		return entity;
	}
}
