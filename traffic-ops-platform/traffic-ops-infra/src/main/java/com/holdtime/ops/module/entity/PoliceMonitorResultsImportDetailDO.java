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
 * 公安监测结果导入详情 DO
 *
 * @author 管理员
 */
@TableName("police_monitor_results_import_detail")
@KeySequence("police_monitor_results_import_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceMonitorResultsImportDetailDO extends BaseDO {

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
     * 设备IP
     */
    private String ipAddr;
    /**
     * 设备标签
     */
    private String equLable;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 实时视频状态
     */
    private String videoStatus;
    /**
     * ping状态
     */
    private String pingStatus;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}