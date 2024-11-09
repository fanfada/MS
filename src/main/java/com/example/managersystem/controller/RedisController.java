package com.example.managersystem.controller;

import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.common.PageResultBody;
import com.example.managersystem.common.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.service.impl.TokenServiceImpl;
import com.example.managersystem.util.ThreadLocalMapUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.helpers.ThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanfada@cmss.chinamobile.com
 * @date 2024/11/07 19:15
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/genToken/{key}")
    public ReturnMessage<String> genToken(@PathVariable(value = "key")final String key){
        return new ReturnMessage<>(ReturnState.OK, this.tokenService.createToken(key));
    }

    @GetMapping(value = "/getValue")
    public ReturnMessage<String> getValue() {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        return new ReturnMessage<>(ReturnState.OK,this.redisCache.getCacheObject(safeUserDto.getId()));
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
