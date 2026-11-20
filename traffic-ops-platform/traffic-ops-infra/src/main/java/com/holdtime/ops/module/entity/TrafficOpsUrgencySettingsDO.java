package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 维保紧急程度配置 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_urgency_settings")
@KeySequence("traffic_ops_urgency_settings_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsUrgencySettingsDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 所属单位：1-交警；2-公安
     */
    private String affiliatedUnit;
    /**
     * 紧急程度
     */
    private String urgencyLevel;
    /**
     * 处理时限小时
     */
    private String deadline;
    /**
     * 通知人员
     */
    private String notifyPersonnel;
    /**
     * 通知人员openid
     */
    private String notifyOpenid;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}