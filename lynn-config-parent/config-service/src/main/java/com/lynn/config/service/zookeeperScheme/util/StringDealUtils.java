/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.service.zookeeperScheme.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Description:需要的对字符串进行处理的工具类
 * 
 * @PackageName:com.lynn.config.bsw.zookeeperScheme.util
 * @ClassName:StringUtils
 * @author xiongweitao
 * @date 2016年2月2日 下午3:06:49
 */
public class StringDealUtils {

	public static String trimEnter(String str) {
		String result = StringUtils.replace(str, "\n", "");
		return result;
	}
}
