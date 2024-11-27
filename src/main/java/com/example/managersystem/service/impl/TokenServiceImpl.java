package com.example.managersystem.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysOperLog;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.enums.BusinessStatus;
import com.example.managersystem.enums.BusinessType;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.manager.AsyncFactory;
import com.example.managersystem.manager.AsyncManager;
import com.example.managersystem.service.SysRoleService;
import com.example.managersystem.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenServiceImpl {


    @Autowired
    private RedisCache redisCache;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleCityServiceImpl sysRoleCityService;

    /**
     * 创建token
     *
     * @return
     */
    public String createToken(final String key) {
        final String tokenKey = "Token:" + key;
        String str = UuidUtil.uuid();
        try {
            StringBuilder token = new StringBuilder(str);
            log.info("生成的token:{}", JsonUtil.toString(token));
            if (this.redisCache.exists(tokenKey)) {
                redisCache.remove(tokenKey);
            }
            this.redisCache.setEx(tokenKey, token.toString(), 1800L);
            boolean notEmpty = StrUtil.isNotEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 校验授权城市
     */
    public void checkAuthorityCities(HttpServletRequest request, final String className, final String methodName, long startTime) {
        final String msg = "查询未授权的城市，请联系管理员授权";
        String userId = request.getHeader("USERID");
        String authStr = this.redisCache.getCacheObject(GlobalConstants.AUTHORITY + userId);
        List<String> authorityCities = Arrays.stream(authStr.split(",")).collect(Collectors.toList());
        String city = request.getParameter("zipCode");
        if (!authorityCities.contains("admin") && !authorityCities.contains(city)) {
            this.generateErrorMsg(msg, className, methodName, startTime);
            throw new GlobalException(msg);
        }
    }

    private void generateErrorMsg(final String msg, final String className, final String methodName, final long startTime) {
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
