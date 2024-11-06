package com.example.managersystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author sunbo@cmss.chinamobile.com
 * @Date 2021/6/21 16:33
 */
@Data
@TableName("os_biz_sec_ssl_instances")
public class InstanceModel {

    /**
     * 实例id
     */
    @TableId("ID")
    private String instanceId;

    /**
     * 证书id
     */
    @TableId("CERT_ID")
    private String certId;

    /**
     * 订单号
     */
    @TableField("ORDER_EXT_ID")
    private String orderId;

    /**
     * 证书类型：DV OV EV
     */
    @TableField("CERTIFICATE_VERSION")
    private String version;

    /**
     * 证书品牌：SHECA Geotrust TrustAsia
     */
    @TableField("CERTIFICATE_TRADEMARK")
    private String trademark;

    /**
     * 加密标准：国际，国密
     */
    @TableField("ENCRYPTION_STANDARD")
    private Integer standard;

    /**
     * 订单创建时间
     */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /**
     * 用户id
     */
    @TableField("PROPOSER")
    private String proposer;

    /**
     * 客户id
     */
    @TableField("CUSTOMER_ID")
    private String customer;

    /**
     * 订单状态，1为删除
     */
    @TableField("IS_DELETE")
    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;

    /**
     * 域名数
     */
    @TableField("DOMAIN_NUMBER")
    private Integer domainNumber;

    /**
     * 产品代码
     */
    @TableField("PRODUCT_NAME")
    private String productName;
}
