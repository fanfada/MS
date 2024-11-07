package com.example.managersystem.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.util.JsonUtil;
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
    public String createToken() {
        String str = UuidUtil.uuid();
        log.info("生成的UUID:{}", str);
        StringBuilder token = new StringBuilder();
        try {
            token.append("token:").append(str);
            log.info("生成的token:{}", JsonUtil.toString(token));
            redisCache.setEx(token.toString(), token.toString(), 10800L);
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
            throw new GlobalException("参数不合法");
        }
        if (!redisCache.exists(token)) {
            throw new GlobalException("请勿重复操作");
        }
        //redis中存在该token，是第一次调用，删除该token并返回true
        boolean remove = redisCache.remove(token);
        if (!remove) {
            throw new GlobalException("请勿重复操作");
        }
        return true;
    }
}
