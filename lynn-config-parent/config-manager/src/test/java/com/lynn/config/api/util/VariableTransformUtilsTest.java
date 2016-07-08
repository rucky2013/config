/*
 * Copyright @ 2016QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.util;

import org.junit.Test;

import com.lynn.config.api.entity.ActiveSystemVariable;

public class VariableTransformUtilsTest {

	@Test
	public void test() {
		ActiveSystemVariable activeSystemVariable = new ActiveSystemVariable();
		activeSystemVariable.setNid("phone_valid_nid");
		activeSystemVariable.setValue("^(13\\d{9}|15\\d{9}|18\\d{9}|145\\d{8}|147\\d{8}|17\\d{9}|120\\d{8})$");
//		String json = VariableTransformUtils.activeSystemVariable2ZkJson(activeSystemVariable);
//		System.out.println(json);
	}
}
