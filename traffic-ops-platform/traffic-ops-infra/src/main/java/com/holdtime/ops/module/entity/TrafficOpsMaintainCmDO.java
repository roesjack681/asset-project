package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备维保耗材 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_maintain_cm")
@KeySequence("traffic_ops_maintain_cm_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintainCmDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 维修进度主键
     */
    private String progressId;
    /**
     * 耗材种类
     */
    private String cmSort;
    /**
     * 耗材数量
     */
    private String cmNum;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}