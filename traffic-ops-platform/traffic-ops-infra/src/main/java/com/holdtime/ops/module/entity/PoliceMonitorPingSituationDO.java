package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 公安通断监测近两日状态 DO
 *
 * @author yanxl
 */
@TableName("police_monitor_ping_situation")
@KeySequence("police_monitor_ping_situation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceMonitorPingSituationDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备id
     */
    private String equId;
    /**
     * 设备IP
     */
    private String ipAddr;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * ping状态
     */
    private String pingStatus;

}