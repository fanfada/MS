package com.example.managersystem.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fanfada@cmss.chinamobile.com
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

    public LoginVo(String userId) {
        this.userId = userId;
    }
}
