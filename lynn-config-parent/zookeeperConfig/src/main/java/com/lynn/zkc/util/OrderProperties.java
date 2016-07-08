package com.lynn.zkc.util;


import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

public class OrderProperties extends Properties {
	private static final long serialVersionUID = -4627607243846121965L;

	private final TreeSet<Object> keys = new TreeSet<Object>();

	public Enumeration<Object> keys() {
		return Collections.<Object> enumeration(keys);
	}

	public Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}

	public Set<Object> keySet() {
		return keys;
	}

	public Set<String> stringPropertyNames() {
		Set<String> set = new TreeSet<String>();
		for (Object key : this.keys) {
			set.add((String) key);
		}
		return set;
	}
}
