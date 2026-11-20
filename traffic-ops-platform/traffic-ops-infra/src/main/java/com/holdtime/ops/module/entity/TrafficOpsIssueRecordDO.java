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
 * 领用记录 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_issue_record")
@KeySequence("traffic_ops_issue_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsIssueRecordDO extends BaseDO {

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
    private String userName;
    /**
     * 领用人编号
     */
    private String userBh;
    /**
     * 领用时间
     */
    private LocalDateTime issueTime;
    /**
     * 领用数量
     */
    private Integer issueNum;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}