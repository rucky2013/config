package com.lynn.config.api.entity;

import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.utils.ValidateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Copyright @ 2013QIANLONG. All right reserved. Class Name :
 * com.lynn.config.core.entity Description : Author : cailinfeng Date :
 * 2016/1/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class SystemVariable extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 系统NAME
	private String systemName;
	// 模块NAME
	private String moduleName;
	// 参数NID
	private String nid;
	// 参数名称
	private String name;
	// 参数值
	private String value;
	// 状态
	private DataStatus status;

	private String remark;
	
	public String getSystemName(){
		if(!ValidateUtil.isNullOrEmpty(systemName)){
			systemName =  systemName.toLowerCase();
		}
		return systemName;
	}
	
	public String getModuleName(){
		if(!ValidateUtil.isNullOrEmpty(moduleName)){
			moduleName =  moduleName.toLowerCase();
		}
		return moduleName;
	}
}
