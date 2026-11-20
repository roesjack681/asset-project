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
 * 护栏保险信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_guardrail_insurance")
@KeySequence("traffic_ops_guardrail_insurance_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsGuardrailInsuranceDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 护栏维护主键
     */
    private String manceGuardrailId;
    /**
     * 车牌号
     */
    private String vehicleNum;
    /**
     * 保险公司名称
     */
    private String insuranceCompany;
    /**
     * 保单编号
     */
    private String insurancePolicy;
    /**
     * 赔付金额
     */
    private BigDecimal payMoney;
    /**
     * 赔付护栏产品编号
     */
    private String productId;
    /**
     * 赔付片数
     */
    private Integer pieces;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}