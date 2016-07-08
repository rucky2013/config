/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.bsw.web;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.api.entity.StaticSystemVariable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "systemVariable")
public class SystemVariableController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemVariableController.class);

	@Resource
	private ActiveSystemVariableManager activeSystemVariableManager;

	@Resource
	private StaticSystemVariableManager staticSystemVariableManager;

	/**
	 * 
	 * Description:根据条件分页查询静态系统变量
	 * 
	 * @param systemName
	 * @param moduleName
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 上午11:06:10
	 */
	@RequestMapping(value = "{systemName}&{moduleName}&{nid}&{name}&{status}/static", method = RequestMethod.GET)
	@ResponseBody
	public PageInfo<StaticSystemVariable> getStaticSystemVariable(StaticSystemVariable staticSystemVariable, Pagenation pagenation,
																  HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			staticSystemVariable.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统静态参数,请求参数为{},{}", JSONObject.toJSONString(staticSystemVariable), JSONObject.toJSONString(pagenation));
		}
		PageInfo<StaticSystemVariable> page = null;
		try {
			// path重新编码(GET请求的路径会被iso-8859-1编码)
			staticSystemVariable.setSystemName(new String(staticSystemVariable.getSystemName().getBytes("ISO-8859-1"), "UTF-8"));
			staticSystemVariable.setModuleName(new String(staticSystemVariable.getModuleName().getBytes("ISO-8859-1"), "UTF-8"));
			staticSystemVariable.setNid(new String(staticSystemVariable.getNid().getBytes("ISO-8859-1"), "UTF-8"));
			staticSystemVariable.setName(new String(staticSystemVariable.getName().getBytes("ISO-8859-1"), "UTF-8"));
			page = staticSystemVariableManager.getStaticSystemVariableByCondition(staticSystemVariable, pagenation);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取系统静态参数成功,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(staticSystemVariable), JSONObject.toJSONString(pagenation),
						JSONObject.toJSONString(page));
			}
		} catch (Exception e) {
			LOGGER.error("获取系统静态参数失败,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(staticSystemVariable), JSONObject.toJSONString(pagenation),
					JSONObject.toJSONString(page), e);
		}
		return page;
	}

	/**
	 * 
	 * Description:根据条件分页查询动态系统变量
	 * 
	 * @param systemName
	 * @param moduleName
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月21日 上午11:06:10
	 */
	@RequestMapping(value = "{systemName}&{moduleName}&{nid}&{name}&{status}/active", method = RequestMethod.GET)
	@ResponseBody
	public PageInfo<ActiveSystemVariable> getActiveSystemVariable(ActiveSystemVariable activeSystemVariable, Pagenation pagenation,
																  HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			activeSystemVariable.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统动态参数,请求参数为{},{}", JSONObject.toJSONString(activeSystemVariable), JSONObject.toJSONString(pagenation));
		}
		PageInfo<ActiveSystemVariable> page = null;
		try {
			// path重新编码(GET请求的路径会被iso-8859-1编码)
			activeSystemVariable.setSystemName(new String(activeSystemVariable.getSystemName().getBytes("ISO-8859-1"), "UTF-8"));
			activeSystemVariable.setModuleName(new String(activeSystemVariable.getModuleName().getBytes("ISO-8859-1"), "UTF-8"));
			activeSystemVariable.setNid(new String(activeSystemVariable.getNid().getBytes("ISO-8859-1"), "UTF-8"));
			activeSystemVariable.setName(new String(activeSystemVariable.getName().getBytes("ISO-8859-1"), "UTF-8"));
			page = activeSystemVariableManager.getActiveSystemVariableByCondition(activeSystemVariable, pagenation);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取系统动态参数成功,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(activeSystemVariable), JSONObject.toJSONString(pagenation),
						JSONObject.toJSONString(page));
			}
		} catch (Exception e) {
			LOGGER.error("获取系统动态参数失败,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(activeSystemVariable), JSONObject.toJSONString(pagenation),
					JSONObject.toJSONString(page), e);
		}
		return page;
	}

	/**
	 * 
	 * Description:获取所有的参数状态
	 * 
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 下午4:22:44
	 */
	@RequestMapping(value = "allDataStatus", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllDataStatus(HttpServletRequest request) {
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
			DataStatus[] dataStatus = DataStatus.values();
			map.put("resultStatus", Constants.System.OK);
			map.put("value", dataStatus);
			entity.setStatus(Constants.System.OK);
			entity.setMsg("获取系统参数状态值成功");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取系统参数状态值成功,请求IP为{}", ipAddress);
			}
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("获取系统参数状态值失败,请求IP为{}", ipAddress, e);
		}
		return entity;
	}

	/**
	 * 
	 * Description:新增或者更新动态系统参数
	 * 
	 * @param activeSystemVariable
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月27日 下午2:29:20
	 */
	@RequestMapping(value = "addOrUpdateSystemVariable/active", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addOrUpdateSystemVariable(ActiveSystemVariable activeSystemVariable, HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			activeSystemVariable.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("新增或更新动态系统参数,请求参数为{}", JSONObject.toJSONString(activeSystemVariable));
		}
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultStatus", Constants.System.FAIL);
		entity.setData(map);
		try {
			if ((ValidateUtil.isNullOrEmpty(activeSystemVariable.getSystemName()) || ValidateUtil.isNullOrEmpty(activeSystemVariable.getModuleName())
					|| ValidateUtil.isNullOrEmpty(activeSystemVariable.getNid()) || ValidateUtil.isNullOrEmpty(activeSystemVariable.getValue()) || ValidateUtil
						.isNullOrEmpty(activeSystemVariable.getName())) && (ValidateUtil.isNullOrEmpty(activeSystemVariable.getId()))) {
				throw new ParamException(ExceptionConstants.Param.PARAM_INVALID, ExceptionConstants.Param.PARAM_INVALID_MSG);
			}
			activeSystemVariableManager.addOrUpdateSystemVariable(activeSystemVariable);
			map.put("resultStatus", Constants.System.OK);
			entity.setStatus(Constants.System.OK);
			entity.setMsg("操作成功!");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("新增或更新动态系统参数成功,请求参数为{},返回参数为{}", JSONObject.toJSONString(activeSystemVariable), JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("新增或更新动态系统参数失败,请求参数为{},返回参数为{}", JSONObject.toJSONString(activeSystemVariable), JSONObject.toJSONString(entity), e);
		} catch (ParamException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("新增或更新动态系统参数失败,请求参数为{},返回参数为{}", JSONObject.toJSONString(activeSystemVariable), JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("新增或更新动态系统参数失败,请求参数为{},返回参数为{}", JSONObject.toJSONString(activeSystemVariable), JSONObject.toJSONString(entity), e);
		}
		return entity;
	}

	/**
	 * 
	 * Description:新增或者更新静态系统参数
	 * 
	 * @param staticSystemVariable
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月27日 下午2:38:47
	 */
	@RequestMapping(value = "addOrUpdateSystemVariable/static", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addOrUpdateSystemVariable(StaticSystemVariable staticSystemVariable, HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			staticSystemVariable.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("新增或更新静态系统参数,请求参数为{}", JSONObject.toJSONString(staticSystemVariable));
		}
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultStatus", Constants.System.FAIL);
		entity.setData(map);
		try {
			if ((ValidateUtil.isNullOrEmpty(staticSystemVariable.getSystemName()) || ValidateUtil.isNullOrEmpty(staticSystemVariable.getModuleName())
					|| ValidateUtil.isNullOrEmpty(staticSystemVariable.getNid()) || ValidateUtil.isNullOrEmpty(staticSystemVariable.getValue()) || ValidateUtil
						.isNullOrEmpty(staticSystemVariable.getName())) && ValidateUtil.isNullOrEmpty(staticSystemVariable.getId())) {
				throw new ParamException(ExceptionConstants.Param.PARAM_INVALID, ExceptionConstants.Param.PARAM_INVALID_MSG);
			}
			staticSystemVariableManager.addOrUpdateSystemVariable(staticSystemVariable);
			map.put("resultStatus", Constants.System.OK);
			entity.setStatus(Constants.System.OK);
			entity.setMsg("操作成功!");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("新增或更新静态系统参数成功,请求参数为{},返回参数为{}", JSONObject.toJSONString(staticSystemVariable), JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("新增或更新动态系统参数失败,请求参数为{},返回参数为{}", JSONObject.toJSONString(staticSystemVariable), JSONObject.toJSONString(entity), e);
		} catch (ParamException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("新增或更新静态系统参数失败,请求参数为{},返回参数为{}", JSONObject.toJSONString(staticSystemVariable), JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("新增或更新静态系统参数失败,请求参数为{},返回参数为{}", JSONObject.toJSONString(staticSystemVariable), JSONObject.toJSONString(entity), e);
		}
		return entity;
	}
}
