package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 检测结果每日分析视图 DO
 *
 * @author 管理员
 */
@TableName("v_ops_monitor_results_daily_analysis")
@KeySequence("v_ops_monitor_results_daily_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewOpsMonitorResultsDailyAnalysisDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String equId;
    /**
     * 杆件编号
     */
    private String memberBh;
    /**
     * 所在道路名称
     */
    private String roadName;
    /**
     * 灯控路口名称
     */
    private String intersectionName;
    /**
     * 方向
     */
    private String direction;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 电警类型
     */
    private String djType;
    /**
     * 设备标签
     */
    private String equLable;
    /**
     * 检测事件
     */
    private String eventType;
    /**
     * 
     */
    private String occurrenceDay;
    /**
     * 
     */
    private BigDecimal normalCnt;
    /**
     * 
     */
    private BigDecimal abnormalCnt;
    /**
     * 
     */
    private Long monitorSumDays;
    /**
     * 
     */
    private Long monitorSumCnt;
    /**
     * 
     */
    private BigDecimal healthRate;

}