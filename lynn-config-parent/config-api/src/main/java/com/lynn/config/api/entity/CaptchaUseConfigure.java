package com.lynn.config.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * Created by fancongchun on 2015/12/11.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_CAPTCHA_USE_CONFIGURE")
public class CaptchaUseConfigure extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private String captchaUse;
    private String remark;
}
