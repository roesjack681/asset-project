package com.holdtime.ops.module.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 违法抓拍设备监控分析 DO
 *
 * @author 管理员
 */
@TableName("view_vio_equipment_monitor_analysis")
@KeySequence("view_vio_equipment_monitor_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewVioEquipmentMonitorAnalysisDO {

    /**
     * 设备id
     */
    @TableId(type = IdType.INPUT)
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
     * 
     */
    private BigDecimal pingNormalCnt;
    /**
     * 
     */
    private BigDecimal pingAbnormalCnt;
    /**
     * 
     */
    private BigDecimal pingHealthRate;
    /**
     * 
     */
    private BigDecimal videoNormalCnt;
    /**
     * 
     */
    private BigDecimal videoAbnormalCnt;
    /**
     * 
     */
    private BigDecimal videoHealthRate;
    /**
     * 
     */
    private BigDecimal noSurveilCnt;
    /**
     * 
     */
    private BigDecimal validSurveilCnt;
    /**
     * 
     */
    private BigDecimal invalidSurveilCnt;
    /**
     * 有效违法率
     */
    private BigDecimal validSurveilRate;
    /**
     * 识别准确率
     */
    private BigDecimal recognitionAccuracyRate;
    /**
     * 识别准确率采集时间
     */
    private LocalDateTime recognitionAccuracyPickDate;
    /**
     * 检测次数
     */
    private BigDecimal monitorCnt;

}