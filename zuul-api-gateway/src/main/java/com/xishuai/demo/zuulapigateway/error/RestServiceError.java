package com.xishuai.demo.zuulapigateway.error;

public class RestServiceError {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static RestServiceError build (int code, String msg) {
        RestServiceError error = new RestServiceError();
        error.code = code;
        error.msg = msg;
        return error;
    }
}
