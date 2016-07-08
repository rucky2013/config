/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.entity;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Description:静态系统参数模版表
 * 
 * @PackageName:com.lynn.config.core.entity
 * @ClassName:StaticSystemVariableTemplate
 * @author xiongweitao
 * @date 2016年3月5日 下午2:57:34
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_SYSTEM_VARIABLE_STATIC_TEMPLATE")
public class StaticSystemVariableTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String moduleName;
	
	private String nid;
}
