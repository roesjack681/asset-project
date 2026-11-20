package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 警用装备报修详情信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_police_maintenance_detail")
@KeySequence("traffic_ops_police_maintenance_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsPoliceMaintenanceDetailDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 报修编号
     */
    private String maintenanceId;
    /**
     * 装备编号
     */
    private String equId;
    /**
     * 问题描述
     */
    private String problem;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}