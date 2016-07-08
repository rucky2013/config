package com.lynn.config.bsw.web;

import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.dto.StaticSystemVariableDto;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.exception.ParamException;
import com.lynn.config.api.manager.ActiveSystemVariableManager;
import com.lynn.config.api.manager.StaticSystemVariableManager;
import com.lynn.config.api.manager.ZkDataPushManager;
import com.lynn.config.api.utils.NetworkUtil;
import com.lynn.config.api.utils.OrderProperties;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Copyright @ 2013QIANLONG. All right reserved. Class Name :
 * com.lynn.config.bsw.web Description : Author : cailinfeng Date :
 * 2016/1/26
 */
@Controller
@RequestMapping(value = "zookeeper")
public class ZkTreeDataController {

	private static Logger logger = LoggerFactory.getLogger(ZkTreeDataController.class);

	@Resource
	private StaticSystemVariableManager staticSystemVariableManager;

	@Resource
	private ActiveSystemVariableManager activeSystemVariableManager;

	@Resource
	private ZkDataPushManager zkDataPushManager;

	/**
	 * 方法 pushTreeToZk 功能描述 ：选择推送某个系统的静态系统变量到zookeeper中(先删除该系统的静态节点,然后重新推送)
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/26
	 * @param staticSystemVariable
	 * @return com.lynn.framework.dto.ResponseEntity
	 *
	 */
	@RequestMapping(value = "pushStaticToZk", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity pushStaticToZk(StaticSystemVariable staticSystemVariable) {
		ResponseEntity responseEntity = new ResponseEntity(Constants.System.OK);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "0");
		Map<String, Map<String, String>> pushResultMap = null;
		try {
			pushResultMap = staticSystemVariableManager.pushTreeDataToZk(staticSystemVariable);
			result.put("result", "1");
			if (logger.isDebugEnabled()) {
				logger.debug("----->推送静态数据到zookeeper成功！参数{}", staticSystemVariable);
			}
			String msg = "";
			if (staticSystemVariable != null && !ValidateUtil.isNullOrEmpty(staticSystemVariable.getSystemName())) {
				msg = "推送" + staticSystemVariable.getSystemName() + "系统静态配置参数到zookeeper成功！";
			} else {
				msg = " 推送所有系统静态配置参数到zookeeper成功！ ";
			}
			responseEntity.setMsg(msg);
		} catch (CommonException e) {
			responseEntity.setStatus(Constants.System.FAIL);
			responseEntity.setError(e.getErrorCode());
			responseEntity.setMsg(e.getErrorMsg());
			logger.error("----->" + e.getErrorMsg() + "参数{}", staticSystemVariable, e);
		} catch (Exception e) {
			responseEntity.setStatus(Constants.System.FAIL);
			responseEntity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			responseEntity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			logger.error("----->推送静态数据到zookeeper异常！参数{}", staticSystemVariable, e);
		} finally {
			result.put("data", pushResultMap);
			responseEntity.setData(result);
		}
		return responseEntity;
	}

	/**
	 * 方法 pushDynamicToZk 功能描述 ：选择推送某个系统、某个模块的动态系统变量到zookeeper中
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/26
	 * @param activeSystemVariable
	 * @return com.lynn.framework.dto.ResponseEntity
	 *
	 */
	@RequestMapping(value = "pushDynamicToZk", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity pushDynamicToZk(ActiveSystemVariable activeSystemVariable) {
		ResponseEntity responseEntity = new ResponseEntity(Constants.System.OK);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "0");
		Map<String, Map<String, String>> pushResultMap = null;
		try {
			pushResultMap = activeSystemVariableManager.pushTreeDataToZk(activeSystemVariable);
			result.put("result", "1");
			if (logger.isDebugEnabled()) {
				logger.debug("----->推送动态数据到zookeeper成功！参数{}", activeSystemVariable);
			}
			String msg = "";
			if (activeSystemVariable != null && !ValidateUtil.isNullOrEmpty(activeSystemVariable.getSystemName())) {
				msg = "推送" + activeSystemVariable.getSystemName() + "系统动态变量到zookeeper成功！";
			} else {
				msg = " 推送所有系统动态变量到zookeeper成功！ ";
			}
			responseEntity.setMsg(msg);
		} catch (CommonException e) {
			responseEntity.setStatus(Constants.System.FAIL);
			responseEntity.setError(e.getErrorCode());
			responseEntity.setMsg(e.getErrorMsg());
			logger.error("----->" + e.getErrorMsg() + "参数{}", activeSystemVariable, e);
		} catch (Exception e) {
			responseEntity.setStatus(Constants.System.FAIL);
			responseEntity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			responseEntity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			logger.error("----->推送动态数据到zookeeper异常！参数{}", activeSystemVariable, e);
		} finally {
			result.put("data", pushResultMap);
			responseEntity.setData(result);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/exportProps/{sSystem}&{dSystem}", method = RequestMethod.GET)
	public void download(StaticSystemVariableDto systemVariable, HttpServletResponse response) {
		OrderProperties props = new OrderProperties();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			if (ValidateUtil.isNullOrEmpty(systemVariable.getSystemName())) {
				if (ValidateUtil.isNullOrEmpty(systemVariable.getSSystem()) && ValidateUtil.isNullOrEmpty(systemVariable.getDSystem())) {
					throw new ParamException(ExceptionConstants.Param.PARAM_INVALID, ExceptionConstants.Param.PARAM_INVALID_MSG);
				}
			}
			Map<String, String> exportMap = activeSystemVariableManager.getZkConfigBySystem(systemVariable);
			if (exportMap != null && exportMap.size() > 0) {
				Set<Map.Entry<String, String>> exportEntry = exportMap.entrySet();
				for (Map.Entry<String, String> entry : exportEntry) {
					props.put(entry.getKey(), entry.getValue() + "");
				}
			}
			outputStream = response.getOutputStream();
			response.setContentType("application/force-download");
			String filename = "conf.properties";
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			props.store(outputStream, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		} catch (ParamException e) {
			logger.error("导出zookeeper参数失败,,请求参数错误，{}", systemVariable, e);
		} catch (Exception e) {
			logger.error("导出zookeeper参数异常, 参数{}", systemVariable, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 
	 * Description:全清zk
	 * 
	 * @param request
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月10日 下午4:31:15
	 */
	@RequestMapping(value = "clean", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity clean(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getIpAddress(request);
		} catch (Exception e) {
			logger.error("获取远程端IP失败", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("清空zookeeper,请求IP为{}", ipAddress);
		}
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(Constants.System.FAIL);
		try {

			zkDataPushManager.clean();
			entity.setStatus(Constants.System.OK);
			entity.setError(ExceptionConstants.System.SERVER_SUCCESS);
			if (logger.isDebugEnabled()) {
				logger.debug("清空zookeeper成功,请求IP为{}", ipAddress);
			}
		} catch (CommonException e) {
			entity.setError(e.getErrorCode());
			entity.setMsg(e.getErrorMsg());
			logger.error("清空zookeeper失败,请求IP为{}", ipAddress, e);
		} catch (Exception e) {
			entity.setError(ExceptionConstants.System.INTERNAL_SERVER_ERROR);
			entity.setMsg(ExceptionConstants.System.INTERNAL_SERVER_ERROR_MSG);
			logger.error("清空zookeeper失败,请求IP为{}", ipAddress, e);
		}
		return entity;
	}
}
