package com.example.managersystem.domain;
import java.io.Serializable;

/**
 * 城市邮编表(SysCityCode)实体类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:35
 */
public class SysCityCode implements Serializable {
    private static final long serialVersionUID = -73930316498677117L;
    /**
     * 城市中文名称
     */
    private String city;
    /**
     * 城市邮政编码
     */
    private String zipcode;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
