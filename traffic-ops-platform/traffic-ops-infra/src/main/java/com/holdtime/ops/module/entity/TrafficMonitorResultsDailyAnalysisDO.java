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
 * 设备监测结果每日分析 DO
 *
 * @author 管理员
 */
@TableName("traffic_monitor_results_daily_analysis")
@KeySequence("traffic_monitor_results_daily_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficMonitorResultsDailyAnalysisDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 分析时间
     */
    private LocalDateTime analysisDate;
    /**
     * 设备ID
     */
    private String equId;
    /**
     * 杆件编号
     */
    private String memberBh;
    /**
     * 道路名称
     */
    private String roadName;
    /**
     * 交叉口名称
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
     * 正常数
     */
    private Integer normalCnt;
    /**
     * 异常数
     */
    private Integer abnormalCnt;
    /**
     * 检测天数
     */
    private Integer monitorSumDays;
    /**
     * 检测总次数
     */
    private Integer monitorSumCnt;
    /**
     * 健康率
     */
    private BigDecimal healthRate;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}