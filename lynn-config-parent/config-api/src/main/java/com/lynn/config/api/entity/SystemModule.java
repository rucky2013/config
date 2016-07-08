/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.entity;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lynn.config.api.constants.enums.DataStatus;
import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.utils.ValidateUtil;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_SYSTEM_MODULE")
public class SystemModule extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 模块名称
	private String moduleName;
	// 父级模块ID
	private String parentModuleId;
	// 模块备注
	private String moduleRemark;
	// 动静态标识
	private ModuleStatus flag;
	// 状态
	private DataStatus status;

	public String getModuleName() {
		if (!ValidateUtil.isNullOrEmpty(moduleName)) {
			moduleName = moduleName.toLowerCase();
		}
		return moduleName;
	}

}
