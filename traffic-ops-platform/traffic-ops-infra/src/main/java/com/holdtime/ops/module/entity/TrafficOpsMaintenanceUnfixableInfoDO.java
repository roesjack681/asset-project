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
 * 设备维保无法修复信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_maintenance_unfixable_info")
@KeySequence("traffic_ops_maintenance_unfixable_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintenanceUnfixableInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 无法修复类型：1-施工；2-设备；3-线路
     */
    private String unfixableType;
    /**
     * 无法修复原因
     */
    private String unfixableReason;
    /**
     * 施工方联系人
     */
    private String constructionContacts;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 预计可维修时间
     */
    private LocalDateTime estimatedRepairDate;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}