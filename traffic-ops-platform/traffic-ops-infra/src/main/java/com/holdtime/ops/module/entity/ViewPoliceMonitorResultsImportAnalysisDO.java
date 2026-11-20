package com.holdtime.ops.module.entity;

import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;

/**
 * VIEW DO
 *
 * @author 管理员
 */
@TableName("v_police_monitor_results_import_analysis")
@KeySequence("v_police_monitor_results_import_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoliceMonitorResultsImportAnalysisDO {

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
     * 设备标签
     */
    private String equLable;
    /**
     * 
     */
    private Integer videoNormalCnt;
    /**
     * 
     */
    private Integer videoAbnormalCnt;
    /**
     * 
     */
    private BigDecimal videoHealthRate;
    /**
     * 
     */
    private Integer pingNormalCnt;
    /**
     * 
     */
    private Integer pingAbnormalCnt;
    /**
     * 
     */
    private BigDecimal pingHealthRate;
    /**
     * 
     */
    private Integer monitorSumCnt;

}