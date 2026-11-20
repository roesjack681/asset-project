package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备监测结果导入信息 DO
 *
 * @author 管理员
 */
@TableName("traffic_monitor_results_import_detail")
@KeySequence("traffic_monitor_results_import_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficMonitorResultsImportDetailDO extends BaseDO {

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
     * IP地址
     */
    private String ipAddr;
    /**
     * 道路名称
     */
    private String roadName;
    /**
     * 路口名称
     */
    private String intersectionName;
    /**
     * 杆件编号
     */
    private String memberBh;
    /**
     * 方向
     */
    private String direction;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 检测情况
     */
    @TableField(updateStrategy =  FieldStrategy.ALWAYS)
    private String monitorSituation;
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
     * 备注人ID
     */
    private Long remarkId;
    /**
     * 备注人姓名
     */
    private String remarkName;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 检测结果图片
     */
    private String resultImg;

}