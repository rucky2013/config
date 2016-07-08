/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.bsw.web;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.exception.ParamException;
import com.lynn.config.api.manager.ActiveSystemVariableManager;
import com.lynn.config.api.manager.StaticSystemVariableManager;
import com.lynn.config.api.utils.NetworkUtil;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.framework.constant.Constants;
import com.lynn.framework.dto.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "configServer")
public class ConfigServerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServerController.class);

	@Resource
	private ActiveSystemVariableManager activeSystemVariableManager;

	@Resource
	private StaticSystemVariableManager staticSystemVariableManager;

	/**
	 * 
	 * Description:根据项目名称(例如:customer)获取静态系统参数
	 * 
	 * @param systemName
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 上午11:06:10
	 */
	@RequestMapping(value = "{systemName}/static", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getStaticSystemVariable(@PathVariable("systemName") String systemName, HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统静态参数,请求系统为{},请求IP为{}", systemName, ipAddress);
		}

		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultStatus", Constants.System.FAIL);
		entity.setData(map);
		try {
			if (ValidateUtil.isNullOrEmpty(systemName)) {
				throw new ParamException(ExceptionConstants.Param.PARAM_INVALID, ExceptionConstants.Param.PARAM_INVALID_MSG);
			}
			// path重新编码(GET请求的路径会被iso-8859-1编码)
			systemName = new String(systemName.getBytes("ISO-8859-1"), "UTF-8");
			Map<String, Object> staticMap = staticSystemVariableManager.getStaticSystemVariableBySystemName(systemName);
			map.put("resultStatus", Constants.System.OK);
			map.put("value", staticMap);
			entity.setStatus(Constants.System.OK);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取系统静态参数成功,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("获取系统静态参数失败,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity), e);
		} catch (ParamException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("获取系统静态参数失败,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("获取系统静态参数失败,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity), e);
		}
		return entity;
	}

	/**
	 * 
	 * Description:根据项目名称(例如:customer)获取动态系统参数
	 * 
	 * @param systemName
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 上午11:06:10
	 */
	@RequestMapping(value = "{systemName}/active", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getActiveSystemVariable(@PathVariable("systemName") String systemName, HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统动态参数,请求系统为{},请求IP为{}", systemName, ipAddress);
		}

		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultStatus", Constants.System.FAIL);
		entity.setData(map);
		try {
			if (ValidateUtil.isNullOrEmpty(systemName)) {
				throw new ParamException(ExceptionConstants.Param.PARAM_INVALID, ExceptionConstants.Param.PARAM_INVALID_MSG);
			}
			// path重新编码(GET请求的路径会被iso-8859-1编码)
			systemName = new String(systemName.getBytes("ISO-8859-1"), "UTF-8");
			Map<String, Map<String, Object>> activeMap = activeSystemVariableManager.getActiveSystemVariableBySystemName(systemName);
			map.put("resultStatus", Constants.System.OK);
			map.put("value", activeMap);
			entity.setStatus(Constants.System.OK);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取系统动态参数成功,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("获取系统动态参数失败,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity), e);
		} catch (ParamException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("获取系统动态参数失败,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("获取系统动态参数失败,请求系统为{},请求IP为{},返回数据为{}", systemName, ipAddress, JSONObject.toJSONString(entity), e);
		}
		return entity;
	}
}
