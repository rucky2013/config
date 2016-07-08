package com.lynn.config.api.dto;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.utils.ValidateUtil;


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
