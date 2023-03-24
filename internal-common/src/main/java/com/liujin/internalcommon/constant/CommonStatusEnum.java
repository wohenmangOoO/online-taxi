package com.liujin.internalcommon.constant;

import lombok.Data;
import lombok.experimental.Accessors;

public enum CommonStatusEnum {

    //成功 CommonStatusEnum SUCCESS = new CommonStatusEnum();
    SUCCESS(1,"success"),
    FAIL(0,"fail");

    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
