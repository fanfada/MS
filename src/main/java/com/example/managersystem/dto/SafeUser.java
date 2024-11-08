package com.example.managersystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SafeUser implements Serializable {

    private static final long serialVersionUID = -8720942077516585405L;

    private String id;

    private Integer isCustomer;

    private String userName;

    /**
     * 用户登录真实ip
     */
    private String realIP;
}
