package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 护栏辅助设备维护详细 DO
 *
 * @author yanxl
 */
@TableName("traffic_ops_maintenance_auxiliary")
@KeySequence("traffic_ops_maintenance_auxiliary_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintenanceAuxiliaryDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 维保主键
     */
    private String maintenanceId;
    /**
     * 护栏主键
     */
    private String guardrailId;
    /**
     * 辅助设备ID
     */
    private String auxiliaryId;
    /**
     * 辅助设备名称
     */
    private String auxiliaryName;
    /**
     * 数量
     */
    private Integer auxiliaryNum;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}