package com.holdtime.ops.module.entity;

import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 违法抓拍设备每日情况 DO
 *
 * @author 管理员
 */
@TableName("view_vio_equipment_daily_situation")
@KeySequence("view_vio_equipment_daily_situation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewVioEquipmentDailySituationDO {

    /**
     * 设备编号
     */
    @TableId(type = IdType.INPUT)
    private String sbbh;
    /**
     * 卡口编号
     */
    private String kkbh;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 
     */
    private LocalDate wfsj;
    /**
     * 
     */
    private BigDecimal dswfCnt;
    /**
     * 
     */
    private BigDecimal yxwfCnt;
    /**
     * 
     */
    private BigDecimal wxwfCnt;
    /**
     * 检测时间
     */
    private LocalDateTime jcsj;
    /**
     * 数据可用率
     */
    private BigDecimal kyl;

}