package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 电警 DO
 *
 * @author 芋道源码
 */
@TableName("police_ops_electric_police")
@KeySequence("police_ops_electric_police_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceOpsElectricPoliceDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备编号
     */
    private String equNumber;
    /**
     * 设备名称
     */
    private String equName;
    /**
     * 设备类型
     */
    private String equType;
    /**
     * 所在道路名称
     */
    private String roadName;
    /**
     * 灯控路口名称
     */
    private String intersectionName;
    /**
     * GPS定位-经度
     */
    private String lng;
    /**
     * GPS定位-纬度
     */
    private String lat;
    /**
     * 安装位置
     */
    private String adress;
    /**
     * 安装时间
     */
    private LocalDateTime installDate;
    /**
     * 厂家
     */
    private String manufacturer;
    /**
     * 型号
     */
    private String equModel;
    /**
     * 归属单位
     */
    private String unit;
    /**
     * 拍摄范围
     */
    private String shootingRange;
    /**
     * 镜头
     */
    private String lens;
    /**
     * 焦距
     */
    private String focalLength;
    /**
     * 光圈
     */
    private String aperture;
    /**
     * 软件版本
     */
    private String softwareVersion;
    /**
     * 安装方式
     */
    private String installationMethod;
    /**
     * 通信方式
     */
    private String communicationModel;
    /**
     * 协议
     */
    private String protocol;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 网关
     */
    private String gateway;
    /**
     * 光交箱位置信息
     */
    private String gjxAddr;
    /**
     * 光交箱GPS定位-经度
     */
    private String gjxLng;
    /**
     * 光交箱GPS定位-纬度
     */
    private String gjxLat;
    /**
     * 方向
     */
    private String direction;
    /**
     * 电警类型
     */
    private String djType;
    /**
     * 路网编号
     */
    private String lwBh;
    /**
     * 集指编号
     */
    private String jzBh;
    /**
     * 管辖单位
     */
    private String dominion;
    /**
     * 状态
     */
    private String status;
    /**
     * 设备图片
     */
    private String equImg;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 杆件编号
     */
    private String memberBh;
    /**
     * 是否核查
     */
    private String isCheck;
    /**
     * 设备标签
     */
    private String equLable;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 安装位置序号
     */
    private String addressXh;
    /**
     * 维保到期时间
     */
    private LocalDateTime maintenanceExpiryDate;
    /**
     * 设备编码
     */
    private String equCode;
    /**
     * 是否在保
     */
    private String underWarranty;
    /**
     * 像素
     */
    private String pixel;

}