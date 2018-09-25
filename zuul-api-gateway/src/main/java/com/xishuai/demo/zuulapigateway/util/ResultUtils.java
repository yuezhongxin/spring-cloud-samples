package com.xishuai.demo.zuulapigateway.util;

import lombok.NonNull;

import java.io.Serializable;

/**
 * Created by yuezhongxin on 2018/9/22.
 */
public class ResultUtils<T> implements Serializable {
    private static final long serialVersionUID = 5583144335966984023L;
    @NonNull
    private Integer code;
    private String msg;
    private T data;

    public static ResultUtils succeed() {
        return succeed((Object)null);
    }

    public static <T> ResultUtils succeed(T data) {
        return succeed(ResultEnum.SUCCESS, data);
    }

    public static <T> ResultUtils succeed(ResultEnum resultEnum, T data) {
        return new ResultUtils(resultEnum.getCode(), resultEnum.getMsg(), data);
    }

    public static ResultUtils fail() {
        return fail(ResultEnum.UNKNOWN_ERROR);
    }

    public static ResultUtils fail(String msg) {
        return fail(ResultEnum.CUSTOM_ERROR, msg);
    }

    public static ResultUtils fail(ResultEnum resultEnum) {
        return fail(resultEnum, "null");
    }

    public static ResultUtils fail(ResultEnum resultEnum, String msg) {
        return fail(resultEnum.getCode(), resultEnum.getMsg() + msg);
    }

    public static ResultUtils fail(Integer code, String msg) {
        return new ResultUtils(code, msg, (Object)null);
    }

    @NonNull
    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(@NonNull Integer code) {
        if (code == null) {
            throw new NullPointerException("code");
        } else {
            this.code = code;
        }
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultUtils)) {
            return false;
        } else {
            ResultUtils<?> other = (ResultUtils)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResultUtils;
    }

    @Override
    public int hashCode() {
        return this.hashCode();

        /*int PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        int result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;*/
    }

    @Override
    public String toString() {
        return "ResultUtils(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public ResultUtils() {
    }

    public ResultUtils(@NonNull Integer code, String msg, T data) {
        if (code == null) {
            throw new NullPointerException("code");
        } else {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }
    }
}
