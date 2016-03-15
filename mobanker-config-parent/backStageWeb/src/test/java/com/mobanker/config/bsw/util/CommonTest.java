/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.bsw.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("all")
public class CommonTest {

	@Test
	public void JsonObjectTest() {
		String json = "{ \"data\":{ \"getStatus\":\"1\" },\"status\":\"1\" }";
		JSONObject jsonObject = JSONObject.parseObject(json);
		jsonObject = jsonObject.getJSONObject("data");
		Map<String, Map<String, Object>> zMap = jsonObject.getObject("value", Map.class);
		System.out.println(JSONObject.toJSONString(zMap));
	}

	@Test
	public void StringArrayTest() {
		String[] sts = new String[10];
		sts[0] = "aa1";
		sts[1] = "aa2";
		sts[2] = "aa3";
		sts[3] = "aa4";
		sts[4] = "aa5";
		String string = Arrays.toString(sts).substring(1);
		System.out.println(string.substring(0, string.length()-1));
	}
	
	@Test
	public void Test() throws UnsupportedEncodingException {
		String path = URLDecoder.decode(URLEncoder.encode(null, "ISO-8859-1"), "UTF-8");
	}
}
