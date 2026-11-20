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
 * 设备维保进度 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_maintain_progress")
@KeySequence("traffic_ops_maintain_progress_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintainProgressDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 所属单位：1-交警；2-公安
     */
    private String affiliatedUnit;
    /**
     * 维保主键
     */
    private String maintenanceId;
    /**
     * 维护情况
     */
    private String maintenance;
    /**
     * 维护人员编号
     */
    private String maintenanceUserId;
    /**
     * 维护人员
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
     * 维护地址
     */
    private String maintenanceAdress;
    /**
     * 维护经度
     */
    private String maintenanceLng;
    /**
     * 维护纬度
     */
    private String maintenanceLat;
    /**
     * 是否换新
     */
    private String isChange;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}