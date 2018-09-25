package com.xishuai.demo.zuulapigateway.util;

/**
 * Created by yuezhongxin on 2018/9/22.
 */
public enum ResultEnum {
    SUCCESS(0, "success"),
    UNKNOWN_ERROR(-1, "unknown error, details: "),
    CUSTOM_ERROR(-100, "custom error, details: ");

    private int code;
    private String msg;

    private ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
