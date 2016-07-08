/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

package com.lynn.config.bsw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class PageController {

	@RequestMapping(value = "{page}", method = RequestMethod.GET)
	public String getPage(@PathVariable("page") String page) {
		return page;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String toIndex() {
		return "index";
	}

//	@RequestMapping("abc")
//	public String abc() {
//		return null;
//	}
}
