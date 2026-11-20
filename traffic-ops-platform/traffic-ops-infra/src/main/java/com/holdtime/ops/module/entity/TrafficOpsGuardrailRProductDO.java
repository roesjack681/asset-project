package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 护栏关联产品信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_guardrail_r_product")
@KeySequence("traffic_ops_guardrail_r_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsGuardrailRProductDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 产品编号
     */
    private String productId;
    /**
     * 护栏编号
     */
    private String guardrailId;
    /**
     * 片数
     */
    private Integer pieces;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}