/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.api.entity.StaticSystemVariable;

/**
 * 
 * Description:转换相关
 * 
 * @PackageName:com.lynn.config.core.util
 * @ClassName:VariableTransformUtils
 * @author xiongweitao
 * @date 2016年1月20日 下午2:44:24
 */
public class VariableTransformUtils {

	private VariableTransformUtils() {
	}

	public static Map<String, Object> activeSystemVariable2Map(ActiveSystemVariable activeSystemVariable) {
		List<ActiveSystemVariable> list = new ArrayList<ActiveSystemVariable>();
		list.add(activeSystemVariable);
		return activeSystemVariable2Map(list);
	}

	public static Map<String, Object> activeSystemVariable2Map(List<ActiveSystemVariable> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (ActiveSystemVariable activeSystemVariable : list) {
			map.put(activeSystemVariable.getNid(), activeSystemVariable.getValue());
		}
		return map;
	}

	public static Map<String, Map<String, Object>> activeSystemVariable2MapGroupByModuleName(List<ActiveSystemVariable> list) {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for (ActiveSystemVariable activeSystemVariable : list) {
			if (ValidateUtil.isNullOrEmpty(map.get(activeSystemVariable.getModuleName()))) {
				Map<String, Object> aMap = new HashMap<String, Object>();
				aMap.put(activeSystemVariable.getNid(), activeSystemVariable.getValue());
				map.put(activeSystemVariable.getModuleName(), aMap);
			} else {
				map.get(activeSystemVariable.getModuleName()).put(activeSystemVariable.getNid(), activeSystemVariable.getValue());
			}
		}
		return map;
	}

	public static Map<String, Object> staticSystemVariable2Map(StaticSystemVariable staticSystemVariable) {
		List<StaticSystemVariable> list = new ArrayList<StaticSystemVariable>();
		list.add(staticSystemVariable);
		return staticSystemVariable2Map(list);
	}

	public static Map<String, Object> staticSystemVariable2Map(List<StaticSystemVariable> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (StaticSystemVariable staticSystemVariable : list) {
			map.put(staticSystemVariable.getNid(), staticSystemVariable.getValue());
		}
		return map;
	}
}
