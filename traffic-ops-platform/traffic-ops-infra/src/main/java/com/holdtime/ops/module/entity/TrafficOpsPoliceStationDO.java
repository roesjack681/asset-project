package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 警用装备采集站 DO
 *
 * @author 杨丽
 */
@TableName("traffic_ops_police_station")
@KeySequence("traffic_ops_police_station_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsPoliceStationDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 使用部门编号
     */
    private String useDeptBh;
    /**
     * 使用部门
     */
    private String useDept;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 存放地址
     */
    private String address;
    /**
     * 设备编号
     */
    private String equCode;
    /**
     * 设备厂商
     */
    private String manufacturer;
    /**
     * 机器名
     */
    private String pcName;
    /**
     * 硬盘
     */
    private String hardDisk;
    /**
     * 处理器
     */
    private String processor;
    /**
     * 操作系统
     */
    private String operatingSystem;
    /**
     * MAC地址
     */
    private String mac;
    /**
     * 内存
     */
    private String memory;
    /**
     * 软件版本
     */
    private String softVersion;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}