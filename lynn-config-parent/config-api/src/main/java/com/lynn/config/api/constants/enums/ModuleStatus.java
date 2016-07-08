package com.lynn.config.api.constants.enums;

/**
 * 
 * Description:
 * 
 * @PackageName:com.lynn.config.core.constants.enums
 * @ClassName:ModuleStatus
 * @author xiongweitao
 * @date 2016年1月25日 下午4:49:49
 */
public enum ModuleStatus {
	ACTIVE("动态"), STATIC("静态"), ALL("共用");
	private String type;

	private ModuleStatus(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
