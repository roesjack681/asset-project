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
 * VIEW DO
 *
 * @author 芋道源码
 */
@TableName("v_ops_maintenance_work_status")
@KeySequence("v_ops_maintenance_work_status_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewOpsMaintenanceWorkStatusDO extends BaseDO {

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
     * 设备编号
     */
    private String equId;
    /**
     * 设备地址
     */
    private String equAddr;
    /**
     * 设备经度
     */
    private String equLng;
    /**
     * 设备纬度
     */
    private String equLat;
    /**
     * 存在问题
     */
    private String problem;
    /**
     * 故障分类
     */
    private String faultType;
    /**
     * 故障时间
     */
    private LocalDateTime faultDate;
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
     * 派单人员编号
     */
    private String dispatchUserId;
    /**
     * 派单人
     */
    private String dispatch;
    /**
     * 派单时间
     */
    private LocalDateTime dispatchTime;
    /**
     * 派单地址
     */
    private String dispatchAdress;
    /**
     * 派单经度
     */
    private String dispatchLng;
    /**
     * 派单纬度
     */
    private String dispatchLat;
    /**
     * 接单地址
     */
    private String receiveAdress;
    /**
     * 接单经度
     */
    private String receiveLng;
    /**
     * 接单纬度
     */
    private String receiveLat;
    /**
     * 接单时间
     */
    private LocalDateTime receiveDate;
    /**
     * 维护建议
     */
    private String suggestion;
    /**
     * 取消人员
     */
    private String cancelPersonnel;
    /**
     * 取消人员编号
     */
    private String cancelUserId;
    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;
    /**
     * 取消原因
     */
    private String cancelReason;
    /**
     * 状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 紧急程度
     */
    private String urgencyLevel;
    /**
     * 最后处理时间
     */
    private LocalDateTime deadline;
    /**
     * 组员人员编号
     */
    private String maintenanceUserId;
    /**
     * 组员姓名
     */
    private String maintenancePersonnel;

}