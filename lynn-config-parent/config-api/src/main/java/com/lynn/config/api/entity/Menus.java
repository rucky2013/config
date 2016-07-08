/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.entity;

import com.lynn.config.api.constants.enums.DataStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_MENUS")
public class Menus extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 菜单ID
	private int menuId;
	// 父菜单ID(顶级默认为0)
	private int parentMenuId;
	// 显示名称
	private String displayName;
	// 目标路径
	private String url;
	
	private DataStatus status;
	// 排序
	private int orderNum;
	// 备注
	private String remark;

	@Transient
	private List<Menus> subMenus;
}
