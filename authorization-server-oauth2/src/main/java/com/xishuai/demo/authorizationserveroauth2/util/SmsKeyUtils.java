package com.xishuai.demo.authorizationserveroauth2.util;

/**
 * Created by yuezhongxin on 2018/9/22.
 */
public class SmsKeyUtils {
    public SmsKeyUtils() {
    }

    public static String getSmsCodeKey(String device, String phone) {
        return String.format("sms-code-%s-%s-code", device, phone);
    }

    public static String getSmsCodeExpireKey(String device, String phone) {
        return String.format("sms-code-%s-%s-expire", device, phone);
    }
}
