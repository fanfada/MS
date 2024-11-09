package com.example.managersystem.common;

import java.util.Arrays;
import java.util.List;

/**
 * 全局常量定义
 */
public class GlobalConstants {

    public static final List<String> IGNORE_URLS = Arrays.asList("/login/login", "/login/registry", "/login/captchaImage");

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 请求header常量
     */
    public static class HttpHeaderConstants {

        /**
         * http请求
         */
        public static final String HTTP = "http://";

        /**
         * https请求
         */
        public static final String HTTPS = "https://";

        /**
         * 请求id
         */
        public static final String TOKEN = "TOKEN";

        /**
         * 请求id
         */
        public static final String REQUEST_ID = "requestId";

        /**
         * 请求id
         */
        public static final String REQUEST_ID_OP = "request_id";

        /**
         * op 接口请求头user_id
         */
        public static final String USER_ID = "USERID";


        /**
         * 用户信息 safe user id
         */
        public static final String SAFE_USER_ID = "safeUserId";

        /**
         * 用户信息 safe user
         */
        public static final String SAFE_USER = "safeUser";
    }

    /**
     * ThreadLocal常量
     */
    public static class ThreadLocalConstants {
        /**
         * 线程共享变量
         */
        public static final String SAFE_SMP_USER = "safeUser";
    }
}
