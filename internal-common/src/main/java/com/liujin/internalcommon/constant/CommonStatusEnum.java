package com.liujin.internalcommon.constant;

import lombok.Data;
import lombok.experimental.Accessors;

public enum CommonStatusEnum {

    TOKEN_ERROR(1099,"token invalid"),

    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),
    VERIFICATION_CODE_SUCCESS(1000,"验证码正确"),
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
