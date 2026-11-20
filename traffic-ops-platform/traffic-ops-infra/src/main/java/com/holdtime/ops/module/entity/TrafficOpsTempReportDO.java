package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 交警运维临时报 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_temp_report")
@KeySequence("traffic_ops_temp_report_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsTempReportDO extends BaseDO {

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
     * IP地址
     */
    private String ipAddr;
    /**
     * 来源：1-接口；2-导入
     */
    private String source;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}