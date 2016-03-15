package com.mobanker.config.service.dto;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.mobanker.config.service.entity.SystemModule;
import com.mobanker.config.service.utils.ValidateUtil;


@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class SystemModuleDto extends SystemModule{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String parentModuleName;
	
	public String getParentModuleName(){
		if(!ValidateUtil.isNullOrEmpty(parentModuleName)){
			parentModuleName = parentModuleName.toLowerCase();
		}
		return parentModuleName;
	}
}
