package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * VIEW DO
 *
 * @author yanxl
 */
@TableName("v_police_monitor_ping_detail_analysis")
@KeySequence("v_police_monitor_ping_detail_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoliceMonitorPingDetailAnalysisDO {

    /**
     * 设备id
     */
    @TableId(type = IdType.INPUT)
    private String equId;
    /**
     * 设备IP
     */
    private String ipAddr;
    /**
     * 
     */
    private BigDecimal autoNormalCnt;
    /**
     * 
     */
    private BigDecimal autoAbnormalCnt;
    /**
     * 
     */
    private BigDecimal autoMonitorCnt;
    /**
     * 
     */
    private BigDecimal autoHealthRate;
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
    private BigDecimal monitorCnt;
    /**
     * 
     */
    private BigDecimal healthRate;

}