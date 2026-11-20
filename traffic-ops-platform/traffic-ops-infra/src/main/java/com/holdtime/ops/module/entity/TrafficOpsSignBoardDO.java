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
 * 标志牌 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_sign_board")
@KeySequence("traffic_ops_sign_board_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsSignBoardDO extends BaseDO {

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
     * 安装时间
     */
    private LocalDateTime installDate;
    /**
     * 安装位置
     */
    private String adress;
    /**
     * GPS定位-经度
     */
    private String lng;
    /**
     * GPS定位-纬度
     */
    private String lat;
    /**
     * 方向
     */
    private String direction;
    /**
     * 标牌内容
     */
    private String labelContent;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 规格单位
     */
    private String ggunit;
    /**
     * 数量
     */
    private String num;
    /**
     * 归属单位
     */
    private String unit;
    /**
     * 所属管辖单位
     */
    private String dominion;
    /**
     * 生产厂家
     */
    private String manufacturer;
    /**
     * 安装厂家
     */
    private String installManufacturer;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 设备图片
     */
    private String equImg;

}