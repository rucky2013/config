package com.lynn.config.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Package Name: com.lynn.config.core.entity Description: Author: qiuyangjun
 * Create Date:2015-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class BaseEntity extends com.lynn.framework.entity.BaseEntity {
	
	private static final long serialVersionUID = 1L;

	// 远程IP
	private String remoteIp;

	// 服务器IP
	private String serverIp;

}
