package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信控交叉口方案信息 DO
 *
 * @author yangli
 */
@TableName("traffic_tsl_junction_plan_info")
@KeySequence("traffic_tsl_junction_plan_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficTslJunctionPlanInfoDO extends BaseDO {

    /**
     * 交叉口ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 交叉口控制id
     */
    private String controllerId;
    /**
     * 周方案
     */
    private String weekPlans;
    /**
     * 假期开始时间
     */
    private LocalDateTime holidaysStartDate;
    /**
     * 假期结束时间
     */
    private LocalDateTime holidaysEndDate;
    /**
     * 假期方案
     */
    private String holidayPlans;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}