package com.example.managersystem.domain;

import java.util.Date;
import java.io.Serializable;

/**
 * 房东信息表(SysOwner)实体类
 *
 * @author makejava
 * @since 2024-11-07 12:36:55
 */
public class SysOwner implements Serializable {
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
     * 状态（0正常 1停用）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
