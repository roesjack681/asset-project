package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 监测电警设备统计 DO
 *
 * @author 管理员
 */
@TableName("traffic_dj_monitor_statistics")
@KeySequence("traffic_dj_monitor_statistics_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficDjMonitorStatisticsDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 检测事件
     */
    private String eventType;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 持续在线时长
     */
    private String cxTime;
    /**
     * 平均在线时长
     */
    private String avgTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}