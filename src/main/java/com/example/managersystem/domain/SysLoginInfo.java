package com.example.managersystem.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author fanfada
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_login_info")
public class SysLoginInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("info_id")
    private Long infoId;

    /**
     * 用户账号
     */
    @TableId("user_name")
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    @TableId("status")
    private String status;

    /**
     * 登录IP地址
     */
    @TableId("ipaddr")
    private String ipaddr;

    /**
     * 登录地点
     */
    @TableId("login_location")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @TableId("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableId("os")
    private String os;

    /**
     * 提示消息
     */
    @TableId("msg")
    private String msg;

    /**
     * 访问时间
     */
    @TableId("login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

}
