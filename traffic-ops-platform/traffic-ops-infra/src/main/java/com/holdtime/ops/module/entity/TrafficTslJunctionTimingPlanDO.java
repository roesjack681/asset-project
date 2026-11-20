package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信控交叉口配时方案信息 DO
 *
 * @author yangli
 */
@TableName("traffic_tsl_junction_timing_plan")
@KeySequence("traffic_tsl_junction_timing_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficTslJunctionTimingPlanDO extends BaseDO {

    /**
     * 主键(交叉口ID+配时方案ID)
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 配时方案ID
     */
    private String planId;
    /**
     * 交叉口ID
     */
    private String junctionId;
    /**
     * 交叉口控制id
     */
    private String controllerId;
    /**
     * 标识
     */
    private String flag;
    /**
     * 相序方案号
     */
    private String stageListId;
    /**
     * 方案数量
     */
    private Integer stageCount;
    /**
     * 方向
     */
    private String direct;
    /**
     * 周期
     */
    private Integer cycleTime;
    /**
     * 相位差
     */
    private Integer phaseDifference;
    /**
     * 是否使用
     */
    private String isUse;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}