/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.bsw.web;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.WebServiceManager;
import com.lynn.config.api.utils.ValidateUtil;
import com.lynn.framework.constant.Constants;
import com.lynn.framework.dto.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@RequestMapping("webService")
public class WebServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceController.class);

	@Resource
	private WebServiceManager webServiceManager;

	/**
	 * 
	 * Description:服务端访问其他服务器的通用接口(GET请求)
	 * 
	 * @param staticSystemVariable
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月2日 下午4:30:13
	 */
	@RequestMapping(value = "get/{systemUrlNid}", method = RequestMethod.GET)
	@ResponseBody
	public Object getRequest(@PathVariable("systemUrlNid") String systemUrlNid, @RequestParam("path") String path, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET访问其他服务器,服务名为{},访问路径为{}", systemUrlNid, path);
		}
		ResponseEntity entity = new ResponseEntity();
		try {
			if (ValidateUtil.isNullOrEmpty(systemUrlNid) || ValidateUtil.isNullOrEmpty(path)) {
				throw new CommonException(ExceptionConstants.System.MISSING_REQUIRED_PARAMETERS,
						ExceptionConstants.System.MISSING_REQUIRED_PARAMETERS_MSG);
			}
			// path重新编码(GET请求的路径会被iso-8859-1编码)
			path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
			// 重组系统参数
			Map<String, String> params = new HashMap<String, String>();
			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				if (entry.getKey().equals("path")) {
					continue;
				}
				if (entry.getValue().length == 1) {
					params.put(entry.getKey(), entry.getValue()[0]);
				}
				if (entry.getValue().length > 1) {
					String s = Arrays.toString(entry.getValue()).substring(1);
					params.put(entry.getKey(), s.substring(0, s.length() - 1));
				}
			}
			Map<String, Object> map = webServiceManager.doGet(systemUrlNid, path, params);
			if (!(boolean) map.get("flag")) {
				return map.get("value");
			}
			entity.setStatus(Constants.System.OK);
			entity.setError(ExceptionConstants.System.SERVER_SUCCESS);
			entity.setData(map.get("value"));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GET访问其他服务器成功,服务名为{},访问路径为{},返回参数为{}", systemUrlNid, path, JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setStatus(Constants.System.OK);
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("GET访问其他服务器失败,服务名为{},访问路径为{},返回参数为{}", systemUrlNid, path, JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setStatus(Constants.System.FAIL);
			entity.setError(ExceptionConstants.System.SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.SERVER_ERROR_MSG);
			LOGGER.error("GET访问其他服务器失败,服务名为{},访问路径为{},返回参数为{}", systemUrlNid, path, JSONObject.toJSONString(entity), e);
		}
		return entity;
	}

	/**
	 * 
	 * Description:服务端访问其他服务器的通用接口(POST请求)
	 * 
	 * @param staticSystemVariable
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月2日 下午4:30:13
	 */
	@RequestMapping(value = "post/{systemUrlNid}", method = RequestMethod.POST)
	@ResponseBody
	public Object postRequest(@PathVariable("systemUrlNid") String systemUrlNid, @RequestParam("path") String path, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST访问其他服务器,服务名为{},访问路径为{}", systemUrlNid, path);
		}
		ResponseEntity entity = new ResponseEntity();
		try {
			if (ValidateUtil.isNullOrEmpty(systemUrlNid) || ValidateUtil.isNullOrEmpty(path)) {
				throw new CommonException(ExceptionConstants.System.MISSING_REQUIRED_PARAMETERS,
						ExceptionConstants.System.MISSING_REQUIRED_PARAMETERS_MSG);
			}
			// 重组系统参数
			Map<String, String> params = new HashMap<String, String>();
			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				if (entry.getKey().equals("path")) {
					continue;
				}
				if (entry.getValue().length == 1) {
					params.put(entry.getKey(), entry.getValue()[0]);
				}
				if (entry.getValue().length > 1) {
					String s = Arrays.toString(entry.getValue()).substring(1);
					params.put(entry.getKey(), s.substring(0, s.length() - 1));
				}
			}
			Map<String, Object> map = webServiceManager.doPost(systemUrlNid, path, params);
			if (!(boolean) map.get("flag")) {
				return map.get("value");
			}
			entity.setStatus(Constants.System.OK);
			entity.setError(ExceptionConstants.System.SERVER_SUCCESS);
			entity.setData(map.get("value"));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("POST访问其他服务器成功,服务名为{},访问路径为{},返回参数为{}", systemUrlNid, path, JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setStatus(Constants.System.OK);
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("POST访问其他服务器失败,服务名为{},访问路径为{},返回参数为{}", systemUrlNid, path, JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setStatus(Constants.System.FAIL);
			entity.setError(ExceptionConstants.System.SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.SERVER_ERROR_MSG);
			LOGGER.error("POST访问其他服务器失败,服务名为{},访问路径为{},返回参数为{}", systemUrlNid, path, JSONObject.toJSONString(entity), e);
		}
		return entity;
	}
}
