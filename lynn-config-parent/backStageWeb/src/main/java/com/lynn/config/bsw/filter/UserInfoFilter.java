package com.lynn.config.bsw.filter;

import com.lynn.config.bsw.constant.ConstantsBSW;
import com.lynn.config.api.entity.Menus;
import com.lynn.config.api.entity.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Copyright @ 2013QIANLONG. All right reserved. Class Name :
 * com.lynn.tkj.cs.back.filter Description : Author : cailinfeng Date :
 * 2015/12/31
 */
public class UserInfoFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String currentURL = request.getRequestURI();
		// 取得请求所对应的绝对路径:
		String targetURL = StringUtils.substring(currentURL, StringUtils.indexOf(currentURL, "/", 1));
		@SuppressWarnings("unchecked")
		List<Menus> menus = (List<Menus>) session.getAttribute(ConstantsBSW.SESSION_MENUS);
		if (menus != null && menus.size() > 0) {
			checkMenuParentId(menus, targetURL, response);
		}

		String[] notFilter = new String[] { "login", "logout", "index.jsp", "login.jsp", ".js", ".css", ".jpg", "ws", ".ico","configServer","quartz","zookeeper/exportProps","zookeeper/clean" };

		// 如果处理HTTP请求，并且需要访问诸如getHeader或getCookies等在ServletRequest中无法得到的方法，就要把此request对象构造成HttpServletRequest

		// 是否过滤
		boolean doFilter = true;
		for (String s : notFilter) {
			if (StringUtils.isNotBlank(targetURL) && StringUtils.indexOf(targetURL, s) != -1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}

		boolean loginSuccess = true;
		boolean menusSuccess = true;
		if (doFilter) {
			if (session.getAttribute(ConstantsBSW.USER_SESSION_KEY) == null) {
				// 如果session内用户信息为空
				loginSuccess = setLoginCookies(request, response, session);
				// loginSuccess = false;
			}
			menusSuccess = session.getAttribute(ConstantsBSW.SESSION_MENUS) != null;
		}
		try {
			if (loginSuccess && menusSuccess) {
				chain.doFilter(servletRequest, servletResponse);
			} else {
				request.getRequestDispatcher("/login.jsp").forward(request, servletResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void checkMenuParentId(List<Menus> menus, String targetURL, HttpServletResponse response) {
		for (Menus menu : menus) {
			if (menu.getSubMenus() != null && menu.getSubMenus().size() > 0) {
				checkMenuParentId(menu.getSubMenus(), targetURL, response);
			} else {
				if (StringUtils.isNotEmpty(targetURL) && targetURL.equals(menu.getUrl())) {
					Cookie cookie = new Cookie(ConstantsBSW.COOKIE_MENU_PARENTID, menu.getParentMenuId() + "");
					response.addCookie(cookie);
					cookie = new Cookie(ConstantsBSW.COOKIE_MENU_ID, menu.getMenuId() + "");
					response.addCookie(cookie);
					return;
				}
			}
		}
	}

	@Override
	public void destroy() {

	}

	private boolean setLoginCookies(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		boolean loginSuccess = false;
		// 判断cookies内是否有用户登录信息,如果有用户登录信息,重建用户登录
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(ConstantsBSW.USER_SESSION_KEY, cookie.getName())) {
					User user = new User();
					user.setUsername(request.getParameter("userName"));
					user.setPassword(request.getParameter("password"));
					if (user != null) {
						loginSuccess = userLoginSuccess(user, request, response);
						break;
					}
				}
			}
		} else {
			loginSuccess = false;
		}
		return loginSuccess;
	}

	private boolean userLoginSuccess(User user, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(ConstantsBSW.USER_SESSION_KEY, user.getUsername());
		cookie.setMaxAge(ConstantsBSW.COOKIES_MAX_AGE);
		cookie.setPath("/");
		response.addCookie(cookie);
		HttpSession session = request.getSession();
		session.setAttribute(ConstantsBSW.USER_SESSION_KEY, user.getUsername());
		if (session != null && session.getAttribute(ConstantsBSW.USER_SESSION_KEY) != null) {
			return true;
		} else {
			return false;
		}
	}
}
