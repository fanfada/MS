package com.example.managersystem.interceptor;
 
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
 * 登录状态校验拦截器
 */
@Slf4j
@Component
public class LoginStatusInterceptor implements HandlerInterceptor {
 
    @Autowired
    private TokenServiceImpl tokenService;
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("登录状态TOKEN校验拦截器执行");
        String path = request.getServletPath();
        log.info("LoginStatusInterceptor.ServletPath:{}", path);
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LoginStatusVerify methodAnnotation = method.getAnnotation(LoginStatusVerify.class);
        if (methodAnnotation != null) {
            check(request);
        }
        return true;
    }
 
    private void check(HttpServletRequest request) throws Exception {
        this.tokenService.checkToken(request);
    }
 
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
 
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    /**
     * 返回的json值
     * @param response
     * @param json
     * @throws Exception
     */
    private void writeReturnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try{
            writer = response.getWriter();
            writer.print(json);
        } catch(IOException e) {
        } finally{
            if(writer != null)
                writer.close();
        }
    }
}