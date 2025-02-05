package com.example.managersystem.manager;

import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysLoginInfo;
import com.example.managersystem.domain.SysOperLog;
import com.example.managersystem.service.ISysLoginInfoService;
import com.example.managersystem.service.ISysOperLogService;
import com.example.managersystem.util.*;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author fanfada
 */
@Slf4j
public class AsyncFactory {

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLoginInfo(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                log.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLoginInfo sysLoginInfo = new SysLoginInfo();
                sysLoginInfo.setUserName(username);
                sysLoginInfo.setIpaddr(ip);
                sysLoginInfo.setLoginLocation(address);
                sysLoginInfo.setBrowser(browser);
                sysLoginInfo.setOs(os);
                sysLoginInfo.setMsg(message);
                // 日志状态
                if (StringUtils.equalsAny(status, GlobalConstants.LOGIN_SUCCESS, GlobalConstants.LOGOUT, GlobalConstants.REGISTER)) {
                    sysLoginInfo.setStatus(GlobalConstants.SUCCESS);
                } else if (GlobalConstants.LOGIN_FAIL.equals(status)) {
                    sysLoginInfo.setStatus(GlobalConstants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(ISysLoginInfoService.class).insertLoginInfo(sysLoginInfo);
            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
