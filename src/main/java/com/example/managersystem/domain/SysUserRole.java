package com.example.managersystem.domain;

import java.io.Serializable;

/**
 * 用户和角色关联表(SysUserRole)实体类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:36
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 353447689779054053L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 角色ID
     */
    private String roleId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
