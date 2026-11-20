package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 区域用户组信息 DO
 *
 * @author 杨丽
 */
@TableName("public_user_area_group")
@KeySequence("public_user_area_group_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicUserAreaGroupDO extends BaseDO {

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
     * 组名
     */
    private String groupName;
    /**
     * 主要运维内容
     */
    private String groupContent;
    /**
     * 可维护区域
     */
    private String groupArea;
    /**
     * 组员id
     */
    private String groupUserId;
    /**
     * 队长id
     */
    private Long leadUserId;
    /**
     * 队长姓名
     */
    private String leadUserName;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}