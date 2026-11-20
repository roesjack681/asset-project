package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 日常巡检信息 DO
 *
 * @author 管理员
 */
@TableName("public_daily_round_check")
@KeySequence("public_daily_round_check_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceDailyRoundCheckDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 巡检设备编号
     */
    private String equId;
    /**
     * 所属单位：1-交警；2-公安
     */
    private String affiliatedUnit;
    /**
     * 设备状态
     */
    private String equStatus;
    /**
     * 巡检项
     */
    private String checkItem;
    /**
     * 问题
     */
    private String question;
    /**
     * 解决方案
     */
    private String solution;
    /**
     * 巡检人
     */
    private String checkPerson;
    /**
     * 巡检方式：内勤；外勤
     */
    private String checkType;
    /**
     * 巡检人编号
     */
    private String checkPersonId;
    /**
     * 巡检时间
     */
    private LocalDateTime checkDate;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}