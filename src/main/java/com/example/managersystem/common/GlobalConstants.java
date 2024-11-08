package com.example.managersystem.common;

/**
 * 全局常量定义
 */
public class GlobalConstants {
    /**
     * 请求header常量
     */
    public static class HttpHeaderConstants {

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
