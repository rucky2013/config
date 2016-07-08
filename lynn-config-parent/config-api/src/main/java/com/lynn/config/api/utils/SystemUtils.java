package com.lynn.config.api.utils;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import com.lynn.config.api.constants.CommonConstants;

/**
 * Created by fancongchun on 2015/11/19.
 */
public class SystemUtils {
	@SuppressWarnings("unused")
	private Integer uNum = 13;

	public static BigInteger getUserId() {
		return new BigInteger("2");
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 判断 (end-start) 时间差是否在指定的hour小时内
	 * 
	 * @author fancongchun 2015/12/1
	 * @param start
	 *            yyyy-M-d HH:mm:ss
	 * @param end
	 *            yyyy-M-d HH:mm:ss
	 * @return boolean
	 */
	public static boolean judgmentDate(Date start, Date end, Integer hour) {
		long cha = end.getTime() - start.getTime();
		if (cha < 0) {
			return false;
		}
		return (cha * 1.0 / (1000 * 60 * 60)) <= hour;
	}

	/**
	 * 
	 * Description:判断当前系统参数可否推送到zk
	 * 本系统不推送
	 * 
	 * @param systemName
	 * @return
	 * @author xiongweitao
	 * @date 2016年3月7日 下午2:58:05
	 */
	public static boolean canPush2ZK(String systemName) {
		return !CommonConstants.SYSTEM_NAME.equals(systemName);
	}
}
