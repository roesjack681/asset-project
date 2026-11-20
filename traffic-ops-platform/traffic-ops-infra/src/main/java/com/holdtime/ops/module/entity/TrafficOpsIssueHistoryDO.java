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
 * 警用装备历史领用记录 DO
 *
 * @author 杨丽
 */
@TableName("traffic_ops_issue_history")
@KeySequence("traffic_ops_issue_history_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsIssueHistoryDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 领用部门编号
     */
    private String useDeptBh;
    /**
     * 领用部门
     */
    private String useDept;
    /**
     * 领用人
     */
    private String issueUserName;
    /**
     * 领用人编号
     */
    private String issueUserBh;
    /**
     * 领用时间
     */
    private LocalDateTime issueTime;
    /**
     * 装备编号
     */
    private String equId;
    /**
     * PSN
     */
    private String psn;
    /**
     * 使用人
     */
    private String userName;
    /**
     * 使用人编号
     */
    private String userBh;
    /**
     * 回收人
     */
    private String recycleUserBh;
    /**
     * 回收人编号
     */
    private String recycleUserName;
    /**
     * 回收时间
     */
    private LocalDateTime recycleTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}