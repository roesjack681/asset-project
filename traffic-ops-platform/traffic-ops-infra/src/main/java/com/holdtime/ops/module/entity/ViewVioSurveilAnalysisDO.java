package com.holdtime.ops.module.entity;

import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;

/**
 * VIEW DO
 *
 * @author 管理员
 */
@TableName("view_vio_surveil_analysis")
@KeySequence("view_vio_surveil_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewVioSurveilAnalysisDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String equId;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 卡口编号
     */
    private String kkbh;
    /**
     * 设备编号
     */
    private String sbbh;
    /**
     * 电警类型
     */
    private String djType;
    /**
     * 设备标签
     */
    private String equLable;
    /**
     * 
     */
    private LocalDate occurrenceDay;
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
    /**
     * 状态
     */
    private String status;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 检测图片
     */
    private String resultImg;
    /**
     * 检测情况
     */
    private String monitorSituation;
    /**
     * 
     */
    private Long noSurveilCnt;
    /**
     * 
     */
    private Long validSurveilCnt;
    /**
     * 
     */
    private Long invalidSurveilCnt;
    /**
     * 识别准确率
     */
    private BigDecimal recognitionAccuracyRate;
    /**
     * 识别准确率采集时间
     */
    private LocalDateTime recognitionAccuracyPickDate;

}