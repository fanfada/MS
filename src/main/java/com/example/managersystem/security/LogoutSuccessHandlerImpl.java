package com.example.managersystem.security;

import com.alibaba.fastjson2.JSON;
import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.dto.AjaxResult;
import com.example.managersystem.dto.LoginUser;
import com.example.managersystem.manager.AsyncManager;
import com.example.managersystem.service.impl.TokenServiceImpl;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.util.ServletUtils;
import com.example.managersystem.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.example.managersystem.manager.AsyncFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author fanfada
 */
@Slf4j
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenServiceImpl tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        log.info("用户登出：{}", JsonUtil.toString(loginUser));
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken(), loginUser.getUserId());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(userName, GlobalConstants.LOGOUT, "登出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("登出成功")));
    }
}
