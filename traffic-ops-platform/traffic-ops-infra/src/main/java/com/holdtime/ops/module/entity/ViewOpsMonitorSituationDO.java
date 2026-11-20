package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * VIEW DO
 *
 * @author 管理员
 */
@TableName("v_ops_monitor_situation")
@KeySequence("v_ops_monitor_situation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewOpsMonitorSituationDO{

    /**
     * 设备ID
     */
    @TableId(type = IdType.INPUT)
    private String equId;
    /**
     * 状态
     */
    private String latestStatus;
    /**
     * 状态
     */
    private String prevStatus;
    /**
     * 状态变化描述
     */
    private String statusChangeDesc;

}