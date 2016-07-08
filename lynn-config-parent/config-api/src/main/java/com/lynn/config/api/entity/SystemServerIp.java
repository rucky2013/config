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

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_SYSTEM_SERVER_IP")
public class SystemServerIp extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 系统ID
	private String systemName;
	// IP
	private String ip;
	// 状态
	private DataStatus status;

}
