/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.dto;

import lombok.Data;

/**
 * 
 * Description:页码数和页面记录数,默认值pageNum = 1;pageSize = 10;
 * 
 * @PackageName:com.lynn.config.core.dto
 * @ClassName:Pagenation
 * @author xiongweitao
 * @date 2016年1月25日 上午10:29:56
 */
@Data
public class Pagenation {
	// 未知功能
	private Integer sEcho;
	// 未知功能
	private Integer iColumns;
	/** 每页开始数标 */
	private Integer iDisplayStart;
	/** 每页显示条数 */
	private Integer iDisplayLength = 10;

	public int getPageNum() {
		int pageNum = 1;
		if (iDisplayLength != null && iDisplayLength >= 1 && iDisplayStart != null && iDisplayStart >= 0) {
			pageNum = iDisplayStart / iDisplayLength + 1;
		}
		return pageNum;
	}

	public int getPageSize() {
		if (iDisplayLength == null || iDisplayLength < 1) {
			iDisplayLength = 10;
		}
		return iDisplayLength;
	}
}
