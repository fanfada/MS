package com.example.managersystem.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysRole;
import com.example.managersystem.domain.SysRoleCity;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.service.SysRoleService;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.util.ThreadLocalMapUtil;
import com.example.managersystem.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenServiceImpl {


    @Autowired
    private RedisCache redisCache;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleCityServiceImpl sysRoleCityService;

    /**
     * 创建token
     *
     * @return
     */
    public String createToken(final String key) {
        String str = UuidUtil.uuid();
        try {
            StringBuilder token = new StringBuilder(str);
            log.info("生成的token:{}", JsonUtil.toString(token));
            if (this.redisCache.exists(key)) {
                redisCache.remove(key);
            }
            this.redisCache.setEx(key, token.toString(), 1800L);
            boolean notEmpty = StrUtil.isNotEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 校验授权城市
     */
    public void checkAuthorityCities(HttpServletRequest request) {
        String authStr = this.redisCache.getCacheObject(GlobalConstants.AUTHORITY);
        List<String> authorityCities = Arrays.stream(authStr.split(",")).collect(Collectors.toList());
        String city = request.getParameter("zipCode");
        if (!authorityCities.contains("admin") && !authorityCities.contains(city)) {
            throw new GlobalException("查询未授权的城市，请联系管理员授权");
        }
    }


    /**
     * 检验token
     *
     * @param request
     */
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader("TOKEN");
        if (StrUtil.isBlank(token)) {
            throw new GlobalException("您未登录，请登录");
        }
        SafeUserDto safeUserDto =
                (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        if (!this.redisCache.exists(safeUserDto.getId())) {
            throw new GlobalException("未分配token或token已过期");
        }
        if (!token.equals(this.redisCache.getCacheObject(safeUserDto.getId()))) {
            throw new GlobalException("token被篡改");
        }
        if (this.redisCache.getExpireTime(safeUserDto.getId()) < 300L) {
            this.redisCache.expire(safeUserDto.getId(), 1800L);
        }
    }
}
