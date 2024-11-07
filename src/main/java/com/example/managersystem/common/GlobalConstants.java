package com.example.managersystem.common;

/**
 * 全局常量定义
 */
public class GlobalConstants {
    /**
     * 系统级常量
     */
    public static class SystemConstants {
        /**
         * eureka.instance.metadata-map
         */
        public static final String MEAT_DATA_MAP_KAY = "gray";

        /**
         * eureka.instance.metadata-map
         */
        public static final String MEAT_DATA_MAP_KAY_VERSION = "version";

        /**
         * 灰度标识(用户、服务)
         */
        public static final String GRAY_Y = "Y";

        /**
         * 正常标识(用户、服务)
         */
        public static final String GRAY_N = "N";

        /**
         * 资源ID, 实例ID
         */
        public static final String RESOURCE_ID = "resourceId";

    }

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
        public static final String USER_ID = "user_id";

        /**
         * op 接口请求头Customer-Id
         */
        public static final String CUSTOMER_ID = "Customer-Id";

        /**
         * 用户信息 op user
         */
        public static final String OP_USER = "ecloudUser";

        /**
         * 用户信息 safe user id
         */
        public static final String SAFE_USER_ID = "safeUserId";

        /**
         * 用户信息 safe user
         */
        public static final String SAFE_USER = "safeUser";

        /**
         * ram权限 token
         */
        public static final String RAM_TOKEN = "ram_token";

        /**
         * 资源池ID(op网关)
         */
        public static final String POOL_ID = "Pool-Id";

        /**
         * 蓝绿流量标识
         */
        public static final String BG_GRAY = "Bg-Gray";

        /**
         * 容量管理请求头
         */
        public static final String AUTH_KEY = "Authorization";
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
