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
 * 护栏辅助设备 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_guardrail_auxiliary")
@KeySequence("traffic_ops_guardrail_auxiliary_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsGuardrailAuxiliaryDO extends BaseDO {

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
     * 辅助设备名称
     */
    private String auxiliaryName;
    /**
     * 数量
     */
    private Integer auxiliaryNum;
    /**
     * 图片
     */
    private String auxiliaryImg;
    /**
     * 状态：安装；拆除
     */
    private String status;
    /**
     * 安装时间
     */
    private LocalDateTime installTime;
    /**
     * 拆除时间
     */
    private LocalDateTime demolishTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}