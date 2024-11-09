package com.example.managersystem.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.util.ThreadLocalMapUtil;
import com.example.managersystem.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class TokenServiceImpl {


    @Autowired
    private RedisCache redisCache;

    /**
     * 创建token
     *
     * @return
     */
    public String createToken(final String key) {
        String str = UuidUtil.uuid();
        StringBuilder token = new StringBuilder();
        try {
            token.append("token:").append(str);
            log.info("生成的token:{}", JsonUtil.toString(token));
            if (redisCache.exists(key)) {
                redisCache.remove(key);
            }
            redisCache.setEx(key, token.toString(), 1800L);
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
     * 检验token
     *
     * @param request
     * @return
     */
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("TOKEN");
        if (StrUtil.isBlank(token)) {
            throw new GlobalException("您未登录，请登录");
        }
        SafeUserDto safeUserDto =
                (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        if (!redisCache.exists(safeUserDto.getId())) {
            throw new GlobalException("未分配token或token已过期");
        }
        if (!token.equals(redisCache.getCacheObject(safeUserDto.getId()))) {
            throw new GlobalException("token被篡改");
        }
        if (this.redisCache.getExpireTime(safeUserDto.getId()) < 300L) {
            this.redisCache.expire(safeUserDto.getId(), 1800L);
        }
        return true;
    }
}
