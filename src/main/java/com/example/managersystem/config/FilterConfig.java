package com.example.managersystem.config;

import com.example.managersystem.filter.ActuatorFilter;
import com.example.managersystem.filter.RequestIdLogMDCFilter;
import com.example.managersystem.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

    /**
     * 请求Id过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<RequestIdLogMDCFilter> requestIdLogFilter() {
        final FilterRegistrationBean<RequestIdLogMDCFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestIdLogMDCFilter());
        registration.setName("requestIdLogFilter");
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 用户id过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<UserFilter> userFilter() {
        final FilterRegistrationBean<UserFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new UserFilter());
        registration.setName("userFilter");
        registration.addUrlPatterns("/api/*");
        registration.setOrder(2);
        return registration;
    }

    /**
     * actuator端点过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<ActuatorFilter> actuatorFilter() {

        final FilterRegistrationBean<ActuatorFilter> registration = new FilterRegistrationBean<>();
        final ActuatorFilter actuatorFilter = new ActuatorFilter();
        registration.setFilter(actuatorFilter);
        registration.addUrlPatterns("/*");
        registration.setName("actuatorFilter");
        registration.setOrder(0);
        return registration;
    }

}