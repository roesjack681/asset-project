package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信控交叉口设备信息 DO
 *
 * @author yangli
 */
@TableName("traffic_tsl_junction_device_info")
@KeySequence("traffic_tsl_junction_device_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficTslJunctionDeviceInfoDO extends BaseDO {

    /**
     * 信号机
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 信号机ID
     * @link TrafficOpsSignalDO.id
     */
    private String signalId;
    /**
     * 信号机名称
     */
    private String controllerName;
    /**
     * 交叉口ID
     */
    private String junctionId;
    /**
     * 交叉口名称
     */
    private String junctionName;
    /**
     * 设备IP
     */
    private String deviceIp;
    /**
     * 在线状态
     */
    private String online;
    /**
     * 状态
     */
    private String state;
    /**
     * 模式
     */
    private String mode;
    /**
     * 协议
     */
    private String protocol;
    /**
     * 能否ping
     */
    private String canPing;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}