/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Description:自定义异常
 * 
 * @PackageName:com.lynn.tkj.cs.core.exception
 * @ClassName:CustomerException
 * @author xiongweitao
 * @date 2015年11月17日 上午10:21:20
 */
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * 自定义错误代码
	 */
	private String errorCode;

	/**
	 * 自定义错误信息
	 */
	private String errorMsg;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		if (StringUtils.isBlank(errorMsg)) {
			return super.getMessage();
		} else {
			return this.errorMsg;
		}
	}

	@Override
	public String getMessage() {
		if (StringUtils.isBlank(errorMsg)) {
			return super.getMessage();
		} else {
			return this.errorMsg;
		}
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public CommonException() {
		super();
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public CommonException(Throwable throwable) {
		super(throwable);
	}

	public CommonException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
