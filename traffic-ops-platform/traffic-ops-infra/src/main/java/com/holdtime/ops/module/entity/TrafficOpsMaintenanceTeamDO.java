package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备维保组员信息 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_maintenance_team")
@KeySequence("traffic_ops_maintenance_team_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintenanceTeamDO extends BaseDO {

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
     * 维保进度id
     */
    private String progressId;
    /**
     * 组员人员编号
     */
    private String maintenanceUserId;
    /**
     * 组员姓名
     */
    private String maintenancePersonnel;
    /**
     * 组员手机号
     */
    private String maintenancePhone;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}