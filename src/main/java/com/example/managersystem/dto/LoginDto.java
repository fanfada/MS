package com.example.managersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录传参
 *
 * @author fanfada
 * @date 2024/11/08 11:12
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    /**
     * 用户账号
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码结果
     */
    private String code;

    /**
     * 验证码uuid：key
     */
    private String uuid;
}
