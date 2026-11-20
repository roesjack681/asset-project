package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 公安监测结果导入近两日状态 DO
 *
 * @author 管理员
 */
@TableName("police_monitor_results_import_situation")
@KeySequence("police_monitor_results_import_situation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceMonitorResultsImportSituationDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备id
     */
    private String equId;
    /**
     * 设备IP
     */
    private String ipAddr;
    /**
     * 设备标签
     */
    private String equLable;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 实时视频状态
     */
    private String videoStatus;
    /**
     * ping状态
     */
    private String pingStatus;

}