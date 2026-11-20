package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备维保无法修复跟进 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_maintenance_unfixable_follow")
@KeySequence("traffic_ops_maintenance_unfixable_follow_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintenanceUnfixableFollowDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 无法修复记录ID
     */
    private String unfixableId;
    /**
     * 协调内容
     */
    private String coordinationContent;
    /**
     * 协调时间
     */
    private LocalDateTime coordinationDate;
    /**
     * 预计修复时间
     */
    private LocalDateTime estimatedRepairDate;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}