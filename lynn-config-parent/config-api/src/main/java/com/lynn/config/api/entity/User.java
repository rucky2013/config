package com.lynn.config.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
//@Table(name = "T_USERS")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long userId;

    //用户名
    private String username;

    //密码
    private String password;

    //支付密码
//    private String paypassword;

    //1正常  0冻结 2注销
    private Integer status;

}