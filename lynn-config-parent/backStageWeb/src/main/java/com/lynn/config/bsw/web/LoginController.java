package com.lynn.config.bsw.web;

import com.lynn.config.api.constants.CommonConstants;
import com.lynn.config.api.entity.Menus;
import com.lynn.config.api.manager.MenusManager;
import com.lynn.config.api.utils.ValidateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Copyright @ 2013QIANLONG. All right reserved. Class Name :
 * com.lynn.tkj.cs.back.web Description : Author : cailinfeng Date :
 * 2016/1/8
 */
@Controller
public class LoginController {

	@Resource
	private MenusManager menusManager;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String userName, @RequestParam String password, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String DEFAULT_USER = "admin";
		String DEFAULT_PASSWORD = "admin";
		if (DEFAULT_USER.equals(userName) && DEFAULT_PASSWORD.equals(password)) {
			// 权限认证
			request.getSession().setAttribute(CommonConstants.USER_SESSION_KEY, userName);
			// 生成菜单内容并放入Session
			HttpSession session = request.getSession();
			if (ValidateUtil.isNullOrEmpty(session.getAttribute(CommonConstants.SESSION_MENUS))) {
				List<Menus> list = menusManager.getAllMenus();
				session.setAttribute(CommonConstants.SESSION_MENUS, list);
			}
			return "redirect:/home";
		} else {
			request.getSession().setAttribute("errorMsg", "用户名或密码错误！");
		}
		return "redirect:/login.jsp";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			break;
		}

		return "redirect:/login.jsp";
	}

}
