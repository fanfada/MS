package com.example.managersystem.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
public class SysRoomVo implements Serializable {
    private static final long serialVersionUID = 503330822141613348L;
    /**
     * 房屋ID
     */
    private Integer id;
    /**
     * 红黑榜：推荐、不推荐、还行
     */
    private String color;
    /**
     * 房屋类型：合租、整租
     */
    private String roomType;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 房屋地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;
    /**
     * 房东ID
     */
    private Integer ownerId;


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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
