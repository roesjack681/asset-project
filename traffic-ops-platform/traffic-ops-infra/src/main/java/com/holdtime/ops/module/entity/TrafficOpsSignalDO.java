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
 * 信号机 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_signal")
@KeySequence("traffic_ops_signal_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsSignalDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
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
     * 联网方式
     */
    private String networkType;
    /**
     * 联网位置
     */
    private String networkAddr;
    /**
     * 信号控制相位
     */
    private String phase;
    /**
     * 联网状态
     */
    private String networkStatus;
    /**
     * 信号机厂家
     */
    private String manufacturer;
    /**
     * 信号机型号
     */
    private String equModel;
    /**
     * 信号机编号
     */
    private String equNumber;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 子网掩码
     */
    private String mask;
    /**
     * 网关
     */
    private String gateway;
    /**
     * 所属环路
     */
    private String belongLoop;
    /**
     * 联网协议
     */
    private String networkProtocol;
    /**
     * 服务器IP地址
     */
    private String serverIp;
    /**
     * 路口全景图
     */
    private String panorama;
    /**
     * 设备箱门禁
     */
    private String accessControl;
    /**
     * 归属单位
     */
    private String unit;

    /**
     * 所属管辖单位
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

}