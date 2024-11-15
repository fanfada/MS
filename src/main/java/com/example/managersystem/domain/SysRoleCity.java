package com.example.managersystem.domain;
import java.io.Serializable;

/**
 * 角色和城市关联表(SysRoleCity)实体类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:35
 */
public class SysRoleCity implements Serializable {
    private static final long serialVersionUID = 847432655637436613L;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 城市邮政编码
     */
    private String zipcode;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
