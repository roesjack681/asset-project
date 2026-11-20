package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 护栏 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_guardrail")
@KeySequence("traffic_ops_guardrail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsGuardrailDO extends BaseDO {

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
     * 起始GPS定位-经度
     */
    private String startLng;
    /**
     * 起始GPS定位-纬度
     */
    private String startLat;
    /**
     * 截止GPS定位-经度
     */
    private String endLng;
    /**
     * 截止GPS定位-纬度
     */
    private String endLat;
    /**
     * 安装位置
     */
    private String adress;
    /**
     * 安装时间
     */
    private LocalDateTime installDate;
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
     * 总片数
     */
    private Integer pieces;
    /**
     * 总长数
     */
    private BigDecimal length;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}