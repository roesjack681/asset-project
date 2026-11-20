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
 * 设备监测结果历史 DO
 *
 * @author 管理员
 */
@TableName("traffic_monitor_results_history")
@KeySequence("traffic_monitor_results_history_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficMonitorResultsHistoryDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 设备类型
     */
    private String equType;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 方向
     */
    private String direction;
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
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 来源：1-接口；2-导入
     */
    private String source;
    /**
     * 设备ID
     */
    private String equId;
    /**
     * 所在道路名称
     */
    private String roadName;
    /**
     * 灯控路口名称
     */
    private String intersectionName;
    /**
     * 杆件编号
     */
    private String memberBh;

}