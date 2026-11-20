package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * VIEW DO
 *
 * @author 管理员
 */
@TableName("v_police_monitor_results_import_situation")
@KeySequence("v_police_monitor_results_import_situation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoliceMonitorResultsImportSituationDO {

    /**
     * 设备id
     */
    @TableId(type = IdType.INPUT)
    private String equId;
    /**
     * 检测时间
     */
    private LocalDateTime latestOccurrenceDay;
    /**
     * 检测时间
     */
    private LocalDateTime previousOccurrenceDay;
    /**
     * ping状态
     */
    private String latestPingStatus;
    /**
     * ping状态
     */
    private String previousPingStatus;
    /**
     * 
     */
    private String finalPingStatus;
    /**
     * 实时视频状态
     */
    private String latestVideoStatus;
    /**
     * 实时视频状态
     */
    private String previousVideoStatus;
    /**
     * 
     */
    private String finalVideoStatus;

}