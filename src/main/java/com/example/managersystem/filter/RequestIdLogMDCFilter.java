package com.example.managersystem.filter;

import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class RequestIdLogMDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader(GlobalConstants.HttpHeaderConstants.REQUEST_ID);
        String requestId = (header == null) ? UuidUtil.uuid() : header;
        MDC.put(GlobalConstants.HttpHeaderConstants.REQUEST_ID, requestId);
        log.info("RequestIdLogMDCFilter设置requestId:{}", requestId);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(GlobalConstants.HttpHeaderConstants.REQUEST_ID);
        }
    }
}
