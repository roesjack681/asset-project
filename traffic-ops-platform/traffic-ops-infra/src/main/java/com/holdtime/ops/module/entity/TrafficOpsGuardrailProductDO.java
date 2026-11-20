package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 护栏产品信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_guardrail_product")
@KeySequence("traffic_ops_guardrail_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsGuardrailProductDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 高度
     */
    private String hight;
    /**
     * 长度
     */
    private BigDecimal length;
    /**
     * 材质
     */
    private String material;
    /**
     * 厂家
     */
    private String manufacturer;
    /**
     * 型号
     */
    private String model;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 底座
     */
    private String pedestal;

}