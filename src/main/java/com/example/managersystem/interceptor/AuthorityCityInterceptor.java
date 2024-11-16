package com.example.managersystem.interceptor;

import com.example.managersystem.annotation.AuthorityCity;
import com.example.managersystem.annotation.LoginStatusVerify;
import com.example.managersystem.service.impl.TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 授权城市校验拦截器
 */
@Slf4j
@Component
public class AuthorityCityInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenServiceImpl tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("授权城市校验拦截器执行");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AuthorityCity methodAnnotation = method.getAnnotation(AuthorityCity.class);
        if (methodAnnotation != null) {
            this.tokenService.checkAuthorityCities(request);
        }
        log.info("授权城市校验拦截器执行结束");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}