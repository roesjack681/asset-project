package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 集指设备检测记录 DO
 *
 * @author 管理员
 */
@TableName("traffic_vio_equipment_monitor_detail")
@KeySequence("traffic_vio_equipment_monitor_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficVioEquipmentMonitorDetailDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 设备id
     */
    private String equId;
    /**
     * 卡口编号
     */
    private String kkbh;
    /**
     * 设备编号
     */
    private String sbbh;
    /**
     * 设备IP
     */
    private String ipAddr;
    /**
     * ping状态
     */
    private String pingStatus;
    /**
     * 实时视频状态
     */
    private String videoStatus;
    /**
     * 无违法次数
     */
    private Integer noSurveilCnt;
    /**
     * 有效违法数
     */
    private Integer validSurveilCnt;
    /**
     * 无效违法数
     */
    private Integer invalidSurveilCnt;
    /**
     * 识别准确率
     */
    private BigDecimal recognitionAccuracyRate;
    /**
     * 识别准确率采集时间
     */
    private LocalDateTime recognitionAccuracyPickDate;

    /**
     * 操作记录
     */
    private String operateMark;

    /**
     * 操作描述
     */
    private String operateDesc;

    /**
     * 操作时间
     */
    private LocalDateTime operateDate;



}