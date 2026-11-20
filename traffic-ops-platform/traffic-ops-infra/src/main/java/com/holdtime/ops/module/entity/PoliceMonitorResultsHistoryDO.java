package com.holdtime.ops.module.entity;

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
 * 公安设备监测结果历史 DO
 *
 * @author yanxl
 */
@TableName("police_monitor_results_history")
@KeySequence("police_monitor_results_history_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceMonitorResultsHistoryDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 检测日期
     */
    private LocalDateTime monitorDate;
    /**
     * 设备类型
     */
    private String equType;
    /**
     * 设备id
     */
    private String equId;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 设备名称
     */
    private String equName;
    /**
     * 杆件编号
     */
    private String memberBh;
    /**
     * 方向
     */
    private String direction;
    /**
     * 检测事件
     */
    private String eventType;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 检测结果图片
     */
    private String resultImg;
    /**
     * 是否为工单
     */
    private String isRepairOrder;
    /**
     * 维保主键
     */
    private String maintenanceId;
    /**
     * 维护人员编号
     */
    private String maintenanceUserId;
    /**
     * 维护人员
     */
    private String maintenancePersonnel;
    /**
     * 维护人员手机号
     */
    private String maintenancePhone;
    /**
     * 设备情况
     */
    private String equSituation;
    /**
     * 设备情况描述
     */
    private String equDesc;
    /**
     * 设备情况证据
     */
    private String evidenceFileId;
    /**
     * 备注时间
     */
    private LocalDateTime remarkDate;
    /**
     * 变更情况：0-未变化；1-已修复；2-未修复
     */
    private String changeSituation;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}