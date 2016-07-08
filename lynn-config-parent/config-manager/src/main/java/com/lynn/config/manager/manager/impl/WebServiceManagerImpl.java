/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.manager.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.lynn.common.utils.HttpClientUtils;
import com.lynn.config.api.constants.CommonConstants;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.ActiveSystemVariableManager;
import com.lynn.config.api.manager.WebServiceManager;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.framework.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebServiceManagerImpl implements WebServiceManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceManagerImpl.class);

	@Resource
	private ActiveSystemVariableManager activeSystemVariableManager;

	@Override
	public Map<String, Object> doGet(String systemUrlNid, String path, Map<String, String> params) throws Exception {
		// 获取请求连接,拼接获取完整url
		String uri = activeSystemVariableManager.getThisSystemVariableByNid(systemUrlNid);
		String url = CommonConstants.HTTP_PREFIX + uri + path;
		LOGGER.info("执行GET请求,请求连接为{},请求参数为{}", url, JSONObject.toJSONString(params));
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		try {
			result = HttpClientUtils.doGet(url, params);
			LOGGER.info("执行GET请求成功,请求连接为{},请求参数为{},返回数据为{}", url, JSONObject.toJSONString(params), result);
		} catch (Exception e) {
			LOGGER.error("执行GET请求失败,请求连接为{},请求参数为{}", url, JSONObject.toJSONString(params), e);
			throw new CommonException(ExceptionConstants.System.SERVER_ERROR, ExceptionConstants.System.SERVER_ERROR_MSG);
		}
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (Constants.System.OK.equals(jsonObject.getString("status"))
				&& ExceptionConstants.System.SERVER_SUCCESS.equals(jsonObject.getString("error"))) {
			map.put("flag", true);
			Object object = null;
			try {
				object = JSONObject.parse(jsonObject.getString("data"));
			} catch (Exception e) {
				object = jsonObject.getString("data");
			}
			map.put("value", object);
		} else if (ValidateUtil.isNullOrEmpty(jsonObject.getString("status")) || ValidateUtil.isNullOrEmpty(jsonObject.getString("error"))) {
			map.put("flag", false);
			Object object = null;
			try {
				object = JSONObject.parse(result);
			} catch (Exception e) {
				object = result;
			}
			map.put("value", object);
		} else {
			// 可能会根据不通的error,抛不同的异常
			throw new CommonException(ExceptionConstants.System.SERVER_ERROR, jsonObject.getString("msg"));
		}
		return map;
	}

	@Override
	public Map<String, Object> doPost(String systemUrlNid, String path, Map<String, String> params) {
		// 获取请求连接,拼接获取完整url
		String uri = activeSystemVariableManager.getThisSystemVariableByNid(systemUrlNid);
		String url = CommonConstants.HTTP_PREFIX + uri + path;
		LOGGER.info("执行POST请求,请求连接为{},请求参数为{}", url, JSONObject.toJSONString(params));
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		try {
			result = HttpClientUtils.doPost(url, params);
			LOGGER.info("执行POST请求成功,请求连接为{},请求参数为{},返回数据为{}", url, JSONObject.toJSONString(params), result);
		} catch (Exception e) {
			LOGGER.error("执行POST请求失败,请求连接为{},请求参数为{}", url, JSONObject.toJSONString(params), e);
			throw new CommonException(ExceptionConstants.System.SERVER_ERROR, ExceptionConstants.System.SERVER_ERROR_MSG);
		}
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (Constants.System.OK.equals(jsonObject.getString("status"))
				&& ExceptionConstants.System.SERVER_SUCCESS.equals(jsonObject.getString("error"))) {
			map.put("flag", true);
			Object object = null;
			try {
				object = JSONObject.parse(jsonObject.getString("data"));
			} catch (Exception e) {
				object = jsonObject.getString("data");
			}
			map.put("value", object);
		} else if (ValidateUtil.isNullOrEmpty(jsonObject.getString("status")) || ValidateUtil.isNullOrEmpty(jsonObject.getString("error"))) {
			map.put("flag", false);
			Object object = null;
			try {
				object = JSONObject.parse(result);
			} catch (Exception e) {
				object = result;
			}
			map.put("value", object);
		} else {
			throw new CommonException(ExceptionConstants.System.SERVER_ERROR, jsonObject.getString("msg"));
		}
		return map;
	}
}
