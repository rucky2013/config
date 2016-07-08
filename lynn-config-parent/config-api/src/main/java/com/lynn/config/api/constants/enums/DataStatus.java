package com.lynn.config.api.constants.enums;

/**
 * Created by wangliyong on 2015/1/30.
 */
public enum DataStatus {
	NORMAL("正常"),DELETED("删除");
    private String type;

    private DataStatus(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
