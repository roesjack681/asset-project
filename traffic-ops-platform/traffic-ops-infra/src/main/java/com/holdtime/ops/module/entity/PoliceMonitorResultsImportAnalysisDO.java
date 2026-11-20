package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 公安监测结果导入分析 DO
 *
 * @author 管理员
 */
@TableName("police_monitor_results_import_analysis")
@KeySequence("police_monitor_results_import_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceMonitorResultsImportAnalysisDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备id
     */
    private String equId;
    /**
     * 设备标签
     */
    private String equLable;
    /**
     * 检测事件
     */
    private String eventType;
    /**
     * 视频正常数
     */
    private Integer videoNormalCnt;
    /**
     * 视频异常数
     */
    private Integer videoAbnormalCnt;
    /**
     * 视频健康率
     */
    private BigDecimal videoHealthRate;
    /**
     * ping正常数
     */
    private Integer pingNormalCnt;
    /**
     * ping异常数
     */
    private Integer pingAbnormalCnt;
    /**
     * ping健康率
     */
    private BigDecimal pingHealthRate;
    /**
     * 检测次数
     */
    private Integer monitorCnt;
    /**
     * 上次修复时间
     */
    private LocalDateTime lastRepairTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}