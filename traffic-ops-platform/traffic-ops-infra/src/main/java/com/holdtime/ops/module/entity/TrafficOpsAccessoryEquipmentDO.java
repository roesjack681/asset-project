package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 杆件附属设备 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_accessory_equipment")
@KeySequence("traffic_ops_accessory_equipment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsAccessoryEquipmentDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 杆件编号
     */
    private String memberId;
    /**
     * 设备类型名称
     */
    private String equType;
    /**
     * 厂家
     */
    private String manufacturer;
    /**
     * 设备型号
     */
    private String equModel;
    /**
     * 数量
     */
    private String equNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}