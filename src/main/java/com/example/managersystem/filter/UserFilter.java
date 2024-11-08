package com.example.managersystem.filter;

import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.common.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.dto.SafeUser;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.util.ThreadLocalMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class UserFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException,
            ServletException {
        try {
            log.info("开始设置用户信息");
            final HttpServletRequest httpRequest = (HttpServletRequest) request;
            final String userId;
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            final String url = httpServletRequest.getRequestURL().toString();
            if (!url.contains("/auth")) {
                userId = httpRequest.getHeader(GlobalConstants.HttpHeaderConstants.USER_ID);
                log.info("请求头里用户信息, userId={}", userId);
                if (StringUtils.isBlank(userId)) {
                    log.info("请求头里用户信息为空");
                    final ReturnMessage<String> returnMessage = new ReturnMessage<>(ReturnState.ERROR);
                    returnMessage.setErrorCode("500");
                    returnMessage.setErrorMessage("用户信息为空。");
                    returnMessage.setRequestId(MDC.get(GlobalConstants.HttpHeaderConstants.REQUEST_ID));
                    response.setContentType("application/json; charset=utf-8");
                    response.getWriter().write(JsonUtil.toString(returnMessage));
                    response.getWriter().flush();
                    return;
                }
                final SafeUser secUser = new SafeUser();
                secUser.setId(userId);
                final String realIP = this.getRealIP(httpRequest);
                log.info("Real IP: {}", realIP);
                secUser.setRealIP(realIP);
                ThreadLocalMapUtil.put(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER, secUser);
                log.info("设置用户信息完成secUser:{}", JsonUtil.toString(secUser));
            }
            chain.doFilter(request, response);
        } finally {
            log.info("清除SecUserContext用户信息");
            ThreadLocalMapUtil.remove(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        }
    }

    private String getRealIP(final HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public void destroy() {

    }
}
