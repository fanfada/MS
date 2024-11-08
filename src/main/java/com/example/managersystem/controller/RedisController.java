package com.example.managersystem.controller;

import com.example.managersystem.common.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.service.impl.TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/genToken")
    public ReturnMessage<String> genToken(){
        return new ReturnMessage<>(ReturnState.OK, this.tokenService.createToken());
    }
}
