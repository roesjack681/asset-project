package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 警用装备资产信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_police_equipment")
@KeySequence("traffic_ops_police_equipment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsPoliceEquipmentDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备名称
     */
    private String equName;
    /**
     * 类型
     */
    private String equType;
    /**
     * 加密卡
     */
    private String encryptionCard;
    /**
     * PSN
     */
    private String psn;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String model;
    /**
     * 生产厂家
     */
    private String manufacturer;
    /**
     * 电池
     */
    private String battery;
    /**
     * 呼号
     */
    private String callSign;
    /**
     * 供货厂家
     */
    private String supplier;
    /**
     * 版本号
     */
    private String version;
    /**
     * 备注
     */
    private String remark;
    /**
     * 录入人编号
     */
    private String recordBh;
    /**
     * 录入人
     */
    private String recorder;
    /**
     * 录入部门编号
     */
    private String recordDeptBh;
    /**
     * 录入部门
     */
    private String recordDept;
    /**
     * 使用部门编号
     */
    private String useDeptBh;
    /**
     * 使用部门
     */
    private String useDept;
    /**
     * 使用人编号
     */
    private String userBh;
    /**
     * 使用人
     */
    private String userName;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}