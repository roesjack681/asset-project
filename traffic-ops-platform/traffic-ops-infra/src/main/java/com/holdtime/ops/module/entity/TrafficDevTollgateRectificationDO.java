package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 卡口质量评价 DO
 *
 * @author 管理员
 */
@TableName("btjczh_dev_tollgate_rectification")
@KeySequence("btjczh_dev_tollgate_rectification_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficDevTollgateRectificationDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 序号
     */
    private String xh;
    /**
     * 卡口编号
     */
    private String kkbh;
    /**
     * 方向类型
     */
    private String fxlx;
    /**
     * 检测时间
     */
    private LocalDateTime jcsj;
    /**
     * 检测不达标原因 1：传输不及时；2：号牌识别准确率低，多个用逗号分隔
     */
    private String bdbyy;
    /**
     * 数据传输及时率
     */
    private BigDecimal jsl;
    /**
     * 号牌识别准确率
     */
    private BigDecimal sbhpzl;
    /**
     * 传输标记
     */
    private String csbj;
    /**
     * 部局传输标记
     */
    private String bjcsbj;
    /**
     * 数据可用率
     */
    private BigDecimal kyl;

}