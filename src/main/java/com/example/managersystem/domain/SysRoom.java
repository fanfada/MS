package com.example.managersystem.domain;

import com.example.managersystem.annotation.ExcelImport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 房屋信息表(SysRoom)实体类
 *
 * @author fanfada
 * @since 2024-11-07 12:36:53
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoom implements Serializable {
    private static final long serialVersionUID = 503330822141613348L;
    /**
     * 房屋ID
     */
    private Integer id;
    /**
     * 红黑榜：推荐、不推荐、还行
     */
    @ExcelImport("红黑榜")
    private String color;
    /**
     * 房屋类型：合租、整租
     */
    @ExcelImport("租赁形式")
    private String roomType;

    /**
     * 城市
     */
    @ExcelImport("城市")
    private String city;
    /**
     * 房屋地址
     */
    @ExcelImport("房屋地址")
    private String address;
    /**
     * 状态（0正常 1停用）
     */
    @ExcelImport("状态")
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
     * 备注
     */
    @ExcelImport(value = "备注", maxLength = 250)
    private String remark;
    /**
     * 房东ID
     */
    @ExcelImport("房东ID")
    private Integer ownerId;

    private String rowTips;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

}
