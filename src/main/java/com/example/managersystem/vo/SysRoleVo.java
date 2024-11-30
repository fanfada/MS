package com.example.managersystem.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息表(SysRoleVo)实体类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:34
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleVo implements Serializable {
    private static final long serialVersionUID = 432054699763982597L;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色权限字符串
     */
    private String roleKey;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限）
     */
    private String dataScope;

    /**
     * 授权的城市
     */
    private List<String> authorityCities;
    /**
     * 备注
     */
    private String remark;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
