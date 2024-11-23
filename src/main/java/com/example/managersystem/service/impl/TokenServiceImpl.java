package com.example.managersystem.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysOperLog;
import com.example.managersystem.dto.LoginUser;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.enums.BusinessStatus;
import com.example.managersystem.enums.BusinessType;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.manager.AsyncFactory;
import com.example.managersystem.manager.AsyncManager;
import com.example.managersystem.service.SysRoleService;
import com.example.managersystem.util.*;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenServiceImpl {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleCityServiceImpl sysRoleCityService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 创建token
     *
     * @return
     */
    public String createToken(LoginUser loginUser) {
        String token = UuidUtil.uuid();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(GlobalConstants.LOGIN_USER_KEY, token);
//        String str = UuidUtil.uuid();
//        try {
//            StringBuilder token = new StringBuilder(str);
//            log.info("生成的token:{}", JsonUtil.toString(token));
//            if (this.redisCache.exists(key)) {
//                redisCache.remove(key);
//            }
//            this.redisCache.setEx(key, token.toString(), 1800L);
//            boolean notEmpty = StrUtil.isNotEmpty(token.toString());
//            if (notEmpty) {
//                return token.toString();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token, String userId) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
            this.redisCache.deleteObject(GlobalConstants.AUTHORITY + userId);
        }
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(GlobalConstants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                LoginUser user = redisCache.getCacheObject(userKey);
                return user;
            } catch (Exception e) {
                throw e;
            }
        }
        return null;
    }


    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(GlobalConstants.TOKEN_PREFIX)) {
            token = token.replace(GlobalConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        log.info("userKey:{}", userKey);
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }


    private String getTokenKey(String uuid) {
        return GlobalConstants.LOGIN_TOKEN_KEY + uuid;
    }

    public String getAuthKey(String uuid) {
        return GlobalConstants.AUTHORITY + uuid;
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr();
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 校验授权城市
     */
    public void checkAuthorityCities(HttpServletRequest request, final String className, final String methodName, long startTime) {
        final String msg = "查询未授权的城市，请联系管理员授权";
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        String authStr = this.redisCache.getCacheObject(GlobalConstants.AUTHORITY + safeUserDto.getId());
        List<String> authorityCities = Arrays.stream(authStr.split(",")).collect(Collectors.toList());
        String city = request.getParameter("zipCode");
        if (!authorityCities.contains("admin") && !authorityCities.contains(city)) {
            this.generateErrorMsg(msg, className, methodName, startTime);
            throw new GlobalException(msg);
        }
    }

    public void generateErrorMsg(final String msg, final String className, final String methodName, final long startTime) {
        try {
            // 获取当前的用户
            SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr();
            operLog.setOperIp(ip);
            operLog.setOperUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
            if (safeUserDto != null) {
                operLog.setOperName(safeUserDto.getId());
            }
            operLog.setStatus(BusinessStatus.FAIL.ordinal());
            operLog.setErrorMsg(StringUtils.substring(msg, 0, 2000));
            operLog.setTitle("校验授权城市");
            operLog.setOperParam("");
            operLog.setJsonResult("");
            operLog.setBusinessType(-1);//未授权
            // 设置方法名称
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            operLog.setCostTime(System.currentTimeMillis() - startTime);
            operLog.setOperatorType(1);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.info("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取当前执行的方法名称
     */
    public static String printCurrentMethodThread() {
        // 获取当前线程的堆栈信息
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // 获取堆栈中的第二个元素，它表示当前正在执行的方法
        StackTraceElement element = stackTrace[2];
        return element.getClassName() + "." + element.getMethodName();
    }

    /**
     * 获取当前执行的方法名称
     */
    public void printCurrentMethodReflect() {
        // 获取当前类的实例
        Class<?> clazz = this.getClass();
        // 获取当前执行方法的名字
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            // 通过反射获取当前方法对象
            Method method = clazz.getMethod(methodName);
            // 输出方法的名称
            System.out.println("Current method: " + method.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检验token
     *
     * @param request
     */
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader("TOKEN");
        if (StrUtil.isBlank(token)) {
            throw new GlobalException("您未登录，请登录");
        }
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        if (!this.redisCache.exists(safeUserDto.getId())) {
            throw new GlobalException("未分配token或token已过期");
        }
        if (!token.equals(this.redisCache.getCacheObject(safeUserDto.getId()))) {
            throw new GlobalException("token被篡改");
        }
        if (this.redisCache.getExpireTime(safeUserDto.getId()) < 300L) {
            this.redisCache.expire(safeUserDto.getId(), 1800L);
        }
    }
}
