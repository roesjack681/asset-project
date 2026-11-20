package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信控交叉口日方案详细信息 DO
 *
 * @author yangli
 */
@TableName("traffic_tsl_junction_day_plan_detail")
@KeySequence("traffic_tsl_junction_day_plan_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficTslJunctionDayPlanDetailDO extends BaseDO {

    /**
     * 主键(交叉口ID+日方案ID+详细ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 详细ID
     */
    private String segmentId;
    /**
     * 日方案ID
     */
    private String dayPlansId;
    /**
     * 交叉口ID
     */
    private String junctionId;
    /**
     * 小时
     */
    private Integer planHour;
    /**
     * 分钟
     */
    private Integer planMinute;
    /**
     * 配时方案号
     */
    private String planId;
    /**
     * 感应方案号
     */
    private String actuId;
    /**
     * 相序方案号
     */
    private String stageListId;
    /**
     * 相位差
     */
    private Integer phaseDifference;
    /**
     * 周期
     */
    private Integer cycleTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}