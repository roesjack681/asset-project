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
 * 信号灯 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_signal_light")
@KeySequence("traffic_ops_signal_light_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsSignalLightDO extends BaseDO {

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
     * 信号灯图案
     */
    private String ledPattern;
    /**
     * 灯具类型
     */
    private String ledType;
    /**
     * 尺寸数据
     */
    private String ledSize;
    /**
     * 灯头数量
     */
    private String ledHeadNum;
    /**
     * 排列方式
     */
    private String arrangement;
    /**
     * 信号显示方式
     */
    private String signalDisplayMode;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
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

}