package com.holdtime.ops.module.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 集指设备检测分析 DO
 *
 * @author 管理员
 */
@TableName("traffic_vio_equipment_monitor_analysis")
@KeySequence("traffic_vio_equipment_monitor_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficVioEquipmentMonitorAnalysisDO extends BaseDO {

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
     * 卡口编号
     */
    private String kkbh;
    /**
     * 设备编号
     */
    private String sbbh;
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
    private Integer monitorCnt;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}