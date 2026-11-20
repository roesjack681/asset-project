package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 集指设备违法原因分析 DO
 *
 * @author 管理员
 */
@TableName("traffic_vio_equipment_illegal_analysis")
@KeySequence("traffic_vio_equipment_illegal_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficVioEquipmentIllegalAnalysisDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备编号
     */
    private String sbbh;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 违法原因
     */
    private String illegalReason;
    /**
     * 违法数量
     */
    private Integer illegalCnt;

}