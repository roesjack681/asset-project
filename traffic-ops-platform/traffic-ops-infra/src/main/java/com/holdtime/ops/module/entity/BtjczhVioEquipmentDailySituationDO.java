package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 集指设备每日情况 DO
 *
 * @author 管理员
 */
@TableName("btjczh_vio_equipment_daily_situation")
@KeySequence("btjczh_vio_equipment_daily_situation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BtjczhVioEquipmentDailySituationDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 检测时间
     */
    private LocalDate occurrenceDay;
    /**
     * 卡口编号
     */
    private String kkbh;
    /**
     * 设备编号
     */
    private String sbbh;
    /**
     * 无违法次数
     */
    private Integer noSurveilCnt;
    /**
     * 有效违法数
     */
    private Integer validSurveilCnt;
    /**
     * 无效违法数
     */
    private Integer invalidSurveilCnt;
    /**
     * 识别准确率
     */
    private BigDecimal recognitionAccuracyRate;
    /**
     * 识别准确率采集时间
     */
    private LocalDateTime recognitionAccuracyPickDate;

}