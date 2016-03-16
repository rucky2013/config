/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.mobanker.config.api.dto;

import com.mobanker.config.api.entity.SystemVariable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class StaticSystemVariableDto extends SystemVariable {

	public String sSystem;

	public String dSystem;

	private static final long serialVersionUID = 1L;
}
