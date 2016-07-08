package com.lynn.config.api.constants;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.tkj.cs.back.constant
 * Description :
 * Author : cailinfeng
 * Date : 2015/12/31
 */
public interface CommonConstants {

    public static final String SESSION_MENUS = "menus";

    public static final String USER_SESSION_KEY="userSessionKey";
    
    //本系统系统名,与数据库对应名称相同,系统模块表中该系统类型设置为动态
	public static final String SYSTEM_NAME = "backstage";
	
	public static final String HTTP_PREFIX = "http://";
	
	public interface ActiveSystemVariableNid {
		
		public static final String SERVER_CHECK_URL_SUFFIX = "server_check_url_suffix";
	}

}
