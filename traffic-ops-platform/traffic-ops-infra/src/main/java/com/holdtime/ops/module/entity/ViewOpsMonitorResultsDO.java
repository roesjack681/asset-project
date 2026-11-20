package com.holdtime.ops.module.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * VIEW DO
 *
 * @author 管理员
 */
@TableName("v_ops_monitor_results")
@KeySequence("v_ops_monitor_results_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewOpsMonitorResultsDO {

    /**
     * 设备ID
     */
    @TableId(type = IdType.INPUT)
    private String equId;
    /**
     * 杆件编号
     */
    private String memberBh;
    /**
     * 所在道路名称
     */
    private String roadName;
    /**
     * 灯控路口名称
     */
    private String intersectionName;
    /**
     * 方向
     */
    private String direction;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 检测事件
     */
    private String eventType;
    /**
     * 正常次数
     */
    private BigDecimal normalCnt;
    /**
     * 异常次数
     */
    private String abnormalCnt;
    /**
     * 总检测天数
     */
    private String monitorSumDays;
    /**
     * 总检测次数
     */
    private String monitorSumCnt;
    /**
     * 健康率
     */
    private Double healthRate;


}