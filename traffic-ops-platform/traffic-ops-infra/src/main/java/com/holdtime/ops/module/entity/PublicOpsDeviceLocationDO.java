package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 终端最新位置 DO
 *
 * @author 管理员
 */
@TableName("public_ops_device_location")
@KeySequence("public_ops_device_location_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicOpsDeviceLocationDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 终端编号
     */
    private String deviceId;
    /**
     * GPS纬度
     */
    private BigDecimal latitude;
    /**
     * GPS经度
     */
    private BigDecimal longitude;
    /**
     * GPS速度(km/h)
     */
    private BigDecimal speed;
    /**
     * 方向(0-359度)
     */
    private Integer direction;
    /**
     * 位置上报时间
     */
    private LocalDateTime reportTime;

}