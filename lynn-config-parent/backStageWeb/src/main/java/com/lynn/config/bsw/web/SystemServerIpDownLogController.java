package com.lynn.config.bsw.web;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.SystemServerIp;
import com.lynn.config.api.entity.SystemServerIpDownLog;
import com.lynn.config.api.manager.SystemServerIpDownLogManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fancongchun on 2016/1/22.
 */
@Controller
@RequestMapping("down")
public class SystemServerIpDownLogController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemServerIpDownLogController.class);
	@Resource
	private SystemServerIpDownLogManager systemServerIpDownLogManager;
	@Resource
	private SystemServerIpManager systemServerIpManager;

	@ResponseBody
	@RequestMapping(value = "{systemName}&{ip}/getSystemServerIpDownLog", method = RequestMethod.GET)
	public PageInfo<SystemServerIpDownLog> getSystemServerIpDownLog(SystemServerIpDownLog systemServerIpDownLog, Pagenation pagenation,
																	HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			systemServerIpDownLog.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统静态参数,请求参数为{},{}", JSONObject.toJSONString(systemServerIpDownLog), JSONObject.toJSONString(pagenation));
		}
		PageInfo<SystemServerIpDownLog> page = null;
		try {
			// path重新编码(GET请求的路径会被iso-8859-1编码)
			systemServerIpDownLog.setSystemName(new String(systemServerIpDownLog.getSystemName().getBytes("ISO-8859-1"), "UTF-8"));
			page = systemServerIpDownLogManager.getSystemServerIpDownLogByCondition(systemServerIpDownLog, pagenation);
		} catch (Exception e) {
			LOGGER.error("获取系统动态参数失败,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(systemServerIpDownLog), JSONObject.toJSONString(pagenation),
					JSONObject.toJSONString(page), e);
		}
		return page;
	}

	@ResponseBody
	@RequestMapping(value = "getSystemServerIp", method = RequestMethod.GET)
	public PageInfo<SystemServerIp> getSystemServerIp(SystemServerIp systemServerIp, Pagenation pagenation, HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			systemServerIp.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统静态参数,请求参数为{},{}", JSONObject.toJSONString(systemServerIp), JSONObject.toJSONString(pagenation));
		}
		PageInfo<SystemServerIp> page = null;
		page = systemServerIpManager.getSystemServerIpByCondition(systemServerIp, pagenation);
		return page;
	}

	@ResponseBody
	@RequestMapping(value = "getSystemName", method = RequestMethod.GET)
	public ResponseEntity getSystemName(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统参数状态值,请求IP为{}", ipAddress);
		}
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultStatus", Constants.System.FAIL);
		entity.setData(map);
		try {
			List<String> list = systemServerIpManager.getgetSystemName();
			map.put("value", list);
			map.put("resultStatus", Constants.System.OK);
			entity.setStatus(Constants.System.OK);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取所有的系统成功,返回数据为{}", JSONObject.toJSONString(entity));
			}
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("获取所有的系统失败,返回数据为{}", JSONObject.toJSONString(entity), e);
		}
		return entity;
	}
}
