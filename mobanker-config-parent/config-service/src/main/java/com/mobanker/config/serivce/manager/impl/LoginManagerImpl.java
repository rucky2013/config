/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.serivce.manager.impl;

import com.mobanker.framework.constant.enums.PermissionResult;
import com.mobanker.framework.dto.LoginResponse;
import com.mobanker.framework.dto.LoginUserDto;
import com.mobanker.framework.exception.DataCommitException;
import com.mobanker.framework.service.LoginService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginManagerImpl implements LoginService {

	public LoginUserDto userLoginSuccess(LoginUserDto user, String token, String loginIp, HttpServletRequest request, HttpServletResponse response)
			throws DataCommitException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean userLoginSuccess(LoginUserDto user, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	public PermissionResult checkPermission(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public LoginResponse login(String loginName, String password, String loginIp, HttpServletRequest request, HttpServletResponse response)
			throws DataCommitException {
		// TODO Auto-generated method stub
		return null;
	}

	public void logout(String userId, String loginIp) throws DataCommitException {
		// TODO Auto-generated method stub

	}
}
