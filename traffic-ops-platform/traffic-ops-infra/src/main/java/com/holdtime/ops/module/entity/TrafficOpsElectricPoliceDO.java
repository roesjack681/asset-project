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
@TableName("traffic_ops_electric_police")
@KeySequence("traffic_ops_electric_police_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsElectricPoliceDO extends BaseDO {

    /**
     * 1.主键-电警编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 2.设备编号-电警编号
     */
    private String equNumber;
    /**
     * 3.设备名称-电警名称
     */
    private String equName;
    /**
     * 4.设备类型-电警类型
     */
    private String equType;
    /**
     * 5.软件版本-软件版本
     */
    private String softwareVersion;
    /**
     * 6.所在道路名称-所在道路名称
     */
    private String roadName;
    /**
     * 7.方向-方向
     */
    private String direction;
    /**
     * 8.灯控路口名称-灯控路口名称
     */
    private String intersectionName;
    /**
     * 9.电警类型-电警类型
     *
     * 枚举 {@link TODO dj_type 对应的类}
     */
    private String djType;
    /**
     * 10.GPS定位-经度-经度
     */
    private String lng;
    /**
     * 11.路网编号-路网编号
     */
    private String lwBh;
    /**
     * 12.GPS定位-纬度-纬度
     */
    private String lat;
    /**
     * 集指编号
     */
    private String jzBh;
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
     * 所属管辖单位
     */
    private String dominion;
    /**
     * 拍摄范围
     */
    private String shootingRange;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
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
     * 状态
     */
    private String status;
    /**
     * 设备图片
     */
    private String equImg;
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
     * 卡口编号
     */
    private String kkbh;
    /**
     * 取证设备编号
     */
    private String sbbh;

    /**
     * 维保到期时间
     */
    private LocalDateTime maintenanceExpiryDate;

    /**
     * 是否在保
     */
    private String underWarranty;
    /**
     * 像素
     */
    private String pixel;
    /**
     * 拍摄方向
     */
    private String shotDirection;
    /**
     * 相机类型
     */
    private String xjType;

}