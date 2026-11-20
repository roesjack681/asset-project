package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 警用装备报修信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_police_maintenance")
@KeySequence("traffic_ops_police_maintenance_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsPoliceMaintenanceDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 上报人员
     */
    private String reportPersonnel;
    /**
     * 上报人员编号
     */
    private String reportUserId;
    /**
     * 上报时间
     */
    private LocalDateTime reportTime;
    /**
     * 上报人员部门编号
     */
    private String reportDeptId;
    /**
     * 上报人员部门
     */
    private String reportDept;
    /**
     * 审核人员编号
     */
    private String auditorBh;
    /**
     * 审核时间
     */
    private LocalDateTime auditDate;
    /**
     * 审核人
     */
    private String auditor;
    /**
     * 审核意见
     */
    private String auditOpinions;
    /**
     * 维修人编号
     */
    private String maintenanceUserId;
    /**
     * 维修人
     */
    private String maintenancePersonnel;
    /**
     * 维护人员手机号
     */
    private String maintenancePhone;
    /**
     * 维护时间
     */
    private LocalDateTime maintenanceDate;
    /**
     * 维护建议
     */
    private String suggestion;
    /**
     * 状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}