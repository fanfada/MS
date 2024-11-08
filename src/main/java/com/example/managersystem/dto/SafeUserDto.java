package com.example.managersystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SafeUserDto implements Serializable {

    private static final long serialVersionUID = -8720942077516585405L;

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户登录真实ip
     */
    private String realIP;
}
