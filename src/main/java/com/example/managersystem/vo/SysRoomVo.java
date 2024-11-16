package com.example.managersystem.vo;

import com.example.managersystem.annotation.ExcelExport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SysRoomVo implements Serializable {
    private static final long serialVersionUID = 503330822141613348L;
    /**
     * 房屋ID
     */
    @ExcelExport(value = "房屋ID", sort = 1)
    private Integer id;
    /**
     * 红黑榜：推荐、不推荐、还行
     */
    @ExcelExport(value = "红黑榜", sort = 6)
    private String color;
    /**
     * 租赁类型：合租、整租
     */
    @ExcelExport(value = "租赁类型", sort = 5)
    private String roomType;
    /**
     * 城市
     */
    @ExcelExport(value = "城市", sort = 3)
    private String city;
    /**
     * 房屋地址
     */
    @ExcelExport(value = "房屋地址", sort = 4)
    private String address;
    /**
     * 备注
     */
    @ExcelExport(value = "备注", sort = 7)
    private String remark;
    /**
     * 房东ID
     */
    @ExcelExport(value = "房东ID", sort = 8)
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
