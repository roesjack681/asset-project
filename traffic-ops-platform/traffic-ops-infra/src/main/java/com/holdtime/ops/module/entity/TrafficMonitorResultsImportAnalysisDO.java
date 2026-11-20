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
 * 设备监测结果导入分析 DO
 *
 * @author 管理员
 */
@TableName("traffic_monitor_results_import_analysis")
@KeySequence("traffic_monitor_results_import_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficMonitorResultsImportAnalysisDO extends BaseDO {

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
     * 正常数
     */
    private Integer normalCnt;
    /**
     * 异常数
     */
    private Integer abnormalCnt;
    /**
     * 健康率
     */
    private BigDecimal healthRate;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}