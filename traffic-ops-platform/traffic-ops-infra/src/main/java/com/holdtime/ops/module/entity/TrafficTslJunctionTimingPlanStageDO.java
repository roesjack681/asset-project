package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信控交叉口配时方案阶段信息 DO
 *
 * @author yangli
 */
@TableName("traffic_tsl_junction_timing_plan_stage")
@KeySequence("traffic_tsl_junction_timing_plan_stage_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficTslJunctionTimingPlanStageDO extends BaseDO {

    /**
     * 主键(交叉口ID+配时方案ID+阶段ID)
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 交叉口ID
     */
    private String junctionId;
    /**
     * 交叉口控制id
     */
    private String controllerId;
    /**
     * 配时方案ID
     */
    private String planId;
    /**
     * 阶段ID
     */
    private String stageId;
    /**
     * 绿灯时间
     */
    private Integer greeTime;
    /**
     * 最短绿灯
     */
    private Integer minGreeTime;
    /**
     * 最长绿灯
     */
    private Integer maxGreeTime;
    /**
     * 黄灯时间
     */
    private Integer yellowTime;
    /**
     * 全红时间
     */
    private Integer allRedTime;
    /**
     * 行闪时间
     */
    private Integer pedFlash;
    /**
     * 行人红灯
     */
    private Integer pedRed;
    /**
     * 绿闪时间
     */
    private Integer greenFlash;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}