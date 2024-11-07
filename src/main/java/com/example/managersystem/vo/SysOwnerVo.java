package com.example.managersystem.vo;

import lombok.Builder;

import java.io.Serializable;

/**
 * 房东信息表(SysOwner)实体类
 *
 * @author fanfada
 * @since 2024-11-07 12:36:55
 */
@Builder
public class SysOwnerVo implements Serializable {
    private static final long serialVersionUID = -10186319665297682L;
    /**
     * 房东ID
     */
    private Integer id;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 房东类型：个人房东、二房东
     */
    private String ownerType;
    /**
     * 红黑榜：推荐、不推荐、还行
     */
    private String color;
    /**
     * 推荐或者不推荐理由
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
