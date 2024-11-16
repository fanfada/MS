package com.example.managersystem.common;

import java.util.Arrays;
import java.util.List;

/**
 * 全局常量定义
 */
public class GlobalConstants {

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Login_Success";

    /**
     * 登出成功
     */
    public static final String LOGOUT_SUCCESS = "Logout_Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Login_Error";

    /**
     * 登录失败
     */
    public static final String LOGOUT_FAIL = "Logout_Error";

    /**
     * 用户过滤器忽略的请求
     */
    public static final List<String> IGNORE_URLS = Arrays.asList("/login/login", "/login/registry", "/login/captchaImage");

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "Captcha_codes:";

    /**
     * 授权城市redis key
     */
    public static final String AUTHORITY = "Authority:";

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
