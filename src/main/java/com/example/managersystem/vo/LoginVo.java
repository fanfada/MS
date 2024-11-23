package com.example.managersystem.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fanfada
 * @date 2024/11/08 11:31
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    /**
     * token
     */
    private String token;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 拥有的权限列表
     */
    private List<String> authorities;

    public LoginVo(String userId) {
        this.userId = userId;
    }
}
