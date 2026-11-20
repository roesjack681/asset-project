package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 护栏开口信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_guardrail_gap")
@KeySequence("traffic_ops_guardrail_gap_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsGuardrailGapDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 护栏主键
     */
    private String guardrailId;
    /**
     * 维保主键
     */
    private String maintenanceId;
    /**
     * 护栏维护主键
     */
    private String manceGuardrailId;
    /**
     * 开口名称
     */
    private String gapName;
    /**
     * 开口类型
     */
    private String gapType;
    /**
     * GPS定位-经度
     */
    private String lng;
    /**
     * GPS定位-纬度
     */
    private String lat;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}