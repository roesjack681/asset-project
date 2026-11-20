package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车辆最新位置查询 DO
 *
 * @author 超级管理员
 */
@TableName("v_device_location_new")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VDeviceLocationNewDO extends PdBaseDO {

    /**
     * IMEI
     */
    private String imei;
    /**
     * 型号
     */
    private String model;
    /**
     * 车牌号
     */
    private String plateNum;

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