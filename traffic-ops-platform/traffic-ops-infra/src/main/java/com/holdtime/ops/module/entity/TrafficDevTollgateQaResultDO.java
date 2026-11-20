package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 卡口质量检测结果 DO
 *
 * @author 管理员
 */
@TableName("btjczh_dev_tollgate_qa_result")
@KeySequence("btjczh_dev_tollgate_qa_result_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficDevTollgateQaResultDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
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
     * 号牌种类
     */
    private String hpzl;
    /**
     * 号牌号码
     */
    private String hphm;
    /**
     * 过车时间
     */
    private LocalDateTime gcsj;
    /**
     * 入队列时间
     */
    private LocalDateTime rdlsj;
    /**
     * 过车序号
     */
    private String gcxh;
    /**
     * 识别号牌种类
     */
    private String sbhpzl;
    /**
     * 识别号牌号码
     */
    private String sbhphm;
    /**
     * 核查标记 默认0：未核查；1：号牌一致；2：号牌不一致
     */
    private String hcbj;
    /**
     * 核查时间
     */
    private LocalDateTime hcsj;
    /**
     * 核查人
     */
    private String hcr;
    /**
     * 数据来源 默认0：自动检测；1：5级以上核对；3：预警签收；4：人工核对
     */
    private String sjly;
    /**
     * 传输标记
     */
    private String csbj;
    /**
     * 部局传输标记
     */
    private String bjcsbj;

}