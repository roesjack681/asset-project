package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 领用详情 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_issue_detail")
@KeySequence("traffic_ops_issue_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsIssueDetailDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 领用编号
     */
    private String issueBh;
    /**
     * 装备编号
     */
    private String equId;
    /**
     * 使用部门编号
     */
    private String useDeptBh;
    /**
     * 使用部门
     */
    private String useDept;
    /**
     * 使用人
     */
    private String userName;
    /**
     * 使用人编号
     */
    private String userBh;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}