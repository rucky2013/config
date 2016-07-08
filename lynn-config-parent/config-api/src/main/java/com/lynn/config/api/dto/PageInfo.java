/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.dto;

import java.util.List;

public class PageInfo<T> extends com.github.pagehelper.PageInfo<T> {

	private static final long serialVersionUID = 1L;

	public long getiTotalRecords() {
		return super.getTotal();
	}

	public long getiTotalDisplayRecords() {
		return super.getTotal();
	}

	// 未知功能
	public int getDraw() {
		return 0;
	}

	public long getRecordsTotal() {
		return super.getTotal();
	}

	public List<?> getData() {
		return super.getList();
	}

	public int getCurrentPage() {
		return super.getPageNum();
	}

	public int getPageCount() {
		return super.getPages();
	}

	// 未知功能
	public int getsEcho() {
		return 0;
	}

	public int getPageSize() {
		return super.getPageSize();
	}

	public PageInfo() {
		super();
	}

	public PageInfo(List<T> list, int navigatePages) {
		super(list, navigatePages);
	}

	public PageInfo(List<T> list) {
		super(list);
	}
}
