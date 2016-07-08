/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.bsw.web;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.dto.SystemModuleDto;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.exception.ParamException;
import com.lynn.config.api.manager.SystemModuleManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "systemModule")
public class SystemModuleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemModuleController.class);

	@Resource
	private SystemModuleManager systemModuleManager;

	/**
	 * 
	 * Description:根据moduleName获取所有有效子模块的名称
	 * 
	 * @param systemModule
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月25日 下午4:46:16
	 */
	@RequestMapping(value = "allSonModuleName/{moduleName}/{flag}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllSonModuleName(SystemModule systemModule, HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			systemModule.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取指定系统的所有模块,请求参数为{}", JSONObject.toJSONString(systemModule));
		}

		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultStatus", Constants.System.FAIL);
		entity.setData(map);
		try {
			if (ValidateUtil.isNullOrEmpty(systemModule.getModuleName()) || ValidateUtil.isNullOrEmpty(systemModule.getFlag())) {
				throw new ParamException(ExceptionConstants.Param.PARAM_INVALID, ExceptionConstants.Param.PARAM_INVALID_MSG);
			}
			// path重新编码(GET请求的路径会被iso-8859-1编码)
			systemModule.setModuleName(new String(systemModule.getModuleName().getBytes("ISO-8859-1"), "UTF-8"));
			List<String> list = systemModuleManager.getSonSystemModuleNameByModuleNameAndFlag(systemModule);
			map.put("resultStatus", Constants.System.OK);
			map.put("value", list);
			entity.setStatus(Constants.System.OK);
			entity.setMsg("获取" + systemModule.getModuleName() + "系统的所有子模块成功");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取指定系统的所有模块成功,请求参数为{},返回数据为{}", JSONObject.toJSONString(systemModule), JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("获取指定系统的所有模块失败,请求参数为{},返回数据为{}", JSONObject.toJSONString(systemModule), JSONObject.toJSONString(entity), e);
		} catch (ParamException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("获取指定系统的所有模块失败,请求参数为{},返回数据为{}", JSONObject.toJSONString(systemModule), JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("获取指定系统的所有模块失败,请求参数为{},返回数据为{}", JSONObject.toJSONString(systemModule), JSONObject.toJSONString(entity), e);
		}
		return entity;
	}

	/**
	 * Copyright @ 2013QIANLONG. All right reserved. className:
	 * Description:获取所有系统下相对应的所有模块
	 * 
	 * @param systemModuleDto
	 * @param Pagenation
	 * @return com.lynn.framework.dto.PageInfo
	 * @author wangtong
	 * @date 2016年1月26日
	 */

	@RequestMapping(value = "getSysModule", method = RequestMethod.GET)
	@ResponseBody
	public PageInfo<SystemModuleDto> getSysModule(SystemModuleDto systemModuleDto, Pagenation pagenation, HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
			systemModuleDto.setRemoteIp(ipAddress);
		} catch (Exception e) {
			LOGGER.error("获取远程端IP失败", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取系统模块,请求参数为{},{}", JSONObject.toJSONString(systemModuleDto), JSONObject.toJSONString(pagenation));
		}
		PageInfo<SystemModuleDto> page = null;
		try {

			page = systemModuleManager.getSystemModuleByCondition(systemModuleDto, pagenation);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取系统模块成功,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(systemModuleDto), JSONObject.toJSONString(pagenation),
						JSONObject.toJSONString(page));
			}
		} catch (CommonException e) {
			LOGGER.error("获取系统模块失败,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(systemModuleDto), JSONObject.toJSONString(pagenation),
					JSONObject.toJSONString(page), e);
		} catch (Exception e) {
			LOGGER.error("获取系统模块失败,请求参数为{},{},返回数据为{}", JSONObject.toJSONString(systemModuleDto), JSONObject.toJSONString(pagenation),
					JSONObject.toJSONString(page), e);
		}
		return page;
	}

	/**
	 * Copyright @ 2013QIANLONG. All right reserved. className:
	 * Description:添加或者更新系统/模块
	 * 
	 * @param systemModuleSto
	 * @param request
	 * @return com.lynn.framework.dto.responseEntity
	 * @author wangtong
	 * @date 2016年1月26日
	 */

	@RequestMapping(value = "addOrUpdateSystemModule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addOrUpdateSystemModule(SystemModuleDto systemModuleDto, HttpServletRequest request) {
		ResponseEntity responseEntity = new ResponseEntity();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("addOrUpdateSystemModule(),参数{}", systemModuleDto);
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put("result", "0");
		responseEntity.setData(data);
		responseEntity.setStatus(Constants.System.OK);
		if (systemModuleDto == null) {
			LOGGER.error("请输入正确的参数信息!", systemModuleDto);
			responseEntity.setMsg(ExceptionConstants.Param.PARAM_INVALID_MSG);
			responseEntity.setError(ExceptionConstants.Param.PARAM_INVALID);
			return responseEntity;
		}
		try {
			systemModuleDto.setRemoteIp(NetworkUtil.getIpAddress(request));
			systemModuleManager.addOrUpdateSystemModule(systemModuleDto);
			data.put("result", "1");
			responseEntity.setMsg("操作成功!");
		} catch (CommonException e) {
			responseEntity.setError(e.getErrorCode());
			responseEntity.setMsg(e.getErrorMsg());
			LOGGER.error("新增/更新系统模块异常，参数为{}", systemModuleDto, e);
		} catch (Exception e) {
			LOGGER.error("新增/更新系统模块异常，addOrUpdateSystemModule(),参数{}", systemModuleDto, e);
			responseEntity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			responseEntity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			responseEntity.setStatus(Constants.System.FAIL);
		}
		System.out.println(responseEntity.getData());
		return responseEntity;
	}

	/**
	 * Copyright @ 2013QIANLONG. All right reserved. className: Description:
	 * 
	 * @param id
	 * @return com.lynn.framework.dto.responseEntity
	 * @author wangtong
	 * @date 2016年1月26日
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity updateStatus(SystemModuleDto systemModuleDto) {
		ResponseEntity responseEntity = new ResponseEntity();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("delSystemModule,参数{}", systemModuleDto);
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put("result", "0");
		responseEntity.setData(data);
		if (systemModuleDto.getStatus() == DataStatus.NORMAL) {
			systemModuleDto.setStatus(DataStatus.DELETED);
		} else {
			systemModuleDto.setStatus(DataStatus.NORMAL);
		}
		try {
			systemModuleManager.addOrUpdateSystemModule(systemModuleDto);
			data.put("result", "1");
			responseEntity.setMsg("系统模块的状态改为<font color='orangered'>" + systemModuleDto.getStatus() + "</font>");
		} catch (Exception e) {
			responseEntity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			responseEntity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			responseEntity.setStatus(Constants.System.FAIL);
			LOGGER.error("更新系统模块异常，updateStatus(),参数{}", systemModuleDto, e);
		}
		return responseEntity;
	}

	@RequestMapping(value = "getSystemName", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getSystemName(SystemModuleDto systemModuleDto) {
		List<String> list = new ArrayList<String>();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取所有系统名");
		}
		try {
			list = systemModuleManager.getSystemName(systemModuleDto);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取所有系统名成功");
			}
		} catch (Exception e) {
			LOGGER.error("getSystemName() error", e);
		}
		return list;
	}

	@RequestMapping(value = "getFlagName", method = RequestMethod.GET)
	@ResponseBody
	public List<ModuleStatus> getFlagName() {
		List<ModuleStatus> list = new ArrayList<ModuleStatus>();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取动静态值");
		}
		list.add(ModuleStatus.STATIC);
		list.add(ModuleStatus.ACTIVE);
		return list;
	}

	@RequestMapping(value = "getStatusName", method = RequestMethod.GET)
	@ResponseBody
	public List<DataStatus> getStatusName() {
		List<DataStatus> list = new ArrayList<DataStatus>();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取状态名");
		}
		list.add(DataStatus.NORMAL);
		list.add(DataStatus.DELETED);
		return list;
	}

	/**
	 * 
	 * Description:获取所有的项目名称
	 * 
	 * @param systemModule
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年1月27日 下午7:08:37
	 */
	@RequestMapping(value = "allSystemName/{moduleStatus}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllSystemName(@PathVariable("moduleStatus") ModuleStatus moduleStatus) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获取所有状态为非{}的系统", moduleStatus.name());
		}

		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultStatus", Constants.System.FAIL);
		entity.setData(map);
		try {
			List<String> list = systemModuleManager.getAllSystemNameNotModuleStatus(moduleStatus);
			map.put("resultStatus", Constants.System.OK);
			map.put("value", list);
			entity.setStatus(Constants.System.OK);
			entity.setMsg("获取所有状态为非" + moduleStatus.name() + "的系统成功");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取所有状态为非{}的系统成功,返回数据为{}", moduleStatus.name(), JSONObject.toJSONString(entity));
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			LOGGER.error("获取所有状态为非{}的系统失败,返回数据为{}", moduleStatus.name(), JSONObject.toJSONString(entity), e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			LOGGER.error("获取所有状态为非{}的系统失败,返回数据为{}", moduleStatus.name(), JSONObject.toJSONString(entity), e);
		}
		return entity;
	}
}
