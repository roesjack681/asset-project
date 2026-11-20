package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 电警通断检测管理 DO
 *
 * @author 管理员
 */
@TableName("police_monitor_ping_analysis")
@KeySequence("police_monitor_ping_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceMonitorPingAnalysisDO extends BaseDO {

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
     * 自动ping正常数
     */
    private Integer autoPingNormalCnt;
    /**
     * 自动ping异常数
     */
    private Integer autoPingAbnormalCnt;
    /**
     * 自动ping健康率
     */
    private BigDecimal autoPingHealthRate;
    /**
     * 自动检测次数
     */
    private Integer autoMonitorCnt;
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
     * 删除时间
     */
    private LocalDateTime deletedTime;

}