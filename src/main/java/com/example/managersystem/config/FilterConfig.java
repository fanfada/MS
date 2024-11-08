package com.example.managersystem.config;

import com.example.managersystem.filter.RequestIdLogMDCFilter;
import com.example.managersystem.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestIdLogMDCFilter> requestIdLogFilter() {

        final FilterRegistrationBean<RequestIdLogMDCFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestIdLogMDCFilter());
        registration.setName("requestIdLogFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        return registration;
    }



    @Bean
    public FilterRegistrationBean<UserFilter> userFilter() {

        final FilterRegistrationBean<UserFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new UserFilter());
        registration.setName("userFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

}