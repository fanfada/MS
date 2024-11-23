package com.example.managersystem.service.impl;

import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysUser;
import com.example.managersystem.dto.LoginUser;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.security.AuthenticationContextHolder;
import com.example.managersystem.service.SysUserService;
import com.example.managersystem.util.SecurityUtils;
import com.example.managersystem.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户验证处理
 *
 * @author fanfada
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = this.sysUserService.queryByPhone(username);
        this.validate(sysUser);
        return createLoginUser(sysUser);
    }

    public UserDetails createLoginUser(SysUser user) {
        String authStr = this.redisCache.getCacheObject(GlobalConstants.AUTHORITY + user.getUserId());
        List<String> authorityCities = Arrays.stream(authStr.split(",")).collect(Collectors.toList());
        return new LoginUser(user.getUserId(), user, authorityCities);
    }

    public void validate(SysUser user) {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();
        log.info("开始验证密码");
        if (!matches(user, password)) {
            throw new GlobalException("密码错误!");
        }
    }

    public boolean matches(SysUser user, String rawPassword) {
        log.info("密码匹配结果:{}", SecurityUtils.matchesPassword(rawPassword, user.getPassword()));
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }
}
