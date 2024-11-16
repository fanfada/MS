package com.example.managersystem.config;

import com.example.managersystem.interceptor.AuthorityCityInterceptor;
import com.example.managersystem.interceptor.LoginStatusInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginStatusInterceptor loginStatusInterceptor;

    @Autowired
    private AuthorityCityInterceptor authorityCityInterceptor;


    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

//        registry.addInterceptor(this.loginStatusInterceptor).addPathPatterns("/**");
        registry.addInterceptor(this.authorityCityInterceptor).addPathPatterns("/**");
    }
}
