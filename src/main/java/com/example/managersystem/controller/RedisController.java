package com.example.managersystem.controller;

import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.dto.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.service.impl.TokenServiceImpl;
import com.example.managersystem.util.ThreadLocalMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanfada
 * @date 2024/11/07 19:15
 */
@Slf4j
@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private RedisCache redisCache;

    @GetMapping(value = "/getValue")
    public ReturnMessage<String> getValue() {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        return new ReturnMessage<>(ReturnState.OK,this.redisCache.getCacheObject(GlobalConstants.AUTHORITY + safeUserDto.getId()));
    }

    @GetMapping(value = "/delKey/{key}")
    public ReturnMessage<Boolean> delKey(@PathVariable(value = "key") final String key) {
        return new ReturnMessage<>(ReturnState.OK, this.redisCache.deleteObject(key));
    }

    @GetMapping(value = "/getExpireTime/{key}")
    public ReturnMessage<Long> getExpireTime(@PathVariable(value = "key") final String key) {
        return new ReturnMessage<>(ReturnState.OK, this.redisCache.getExpireTime(key));
    }
}
