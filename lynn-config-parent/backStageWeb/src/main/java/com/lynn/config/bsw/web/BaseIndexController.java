package com.lynn.config.bsw.web;

import com.lynn.framework.constant.SysConstants;
import com.lynn.framework.utils.SessionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Package Name: com.lynn.tkj.cs.back.web Description: Author: qiuyangjun
 * Create Date:2015-12-29
 */
@Controller
@RequestMapping("/{moduleName}/{submoduleName}")
public class BaseIndexController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(produces = "text/html")
	public String index(@PathVariable String moduleName, @PathVariable String submoduleName, Model model) {
		// path重新编码(GET请求的路径会被iso-8859-1编码)
		// moduleName = new String(moduleName.getBytes("ISO-8859-1"), "UTF-8");
		// moduleName = new String(moduleName.getBytes("ISO-8859-1"), "UTF-8");
		model.addAttribute("moduleName", moduleName);
		model.addAttribute("submoduleName", submoduleName);
		model.addAttribute(SysConstants.USER_SESSION_KEY, SessionUtils.getUser());
		return moduleName + "." + submoduleName;
	}
}
