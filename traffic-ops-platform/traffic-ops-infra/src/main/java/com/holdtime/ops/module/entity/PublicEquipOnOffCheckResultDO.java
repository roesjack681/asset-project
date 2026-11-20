package com.holdtime.ops.module.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备通断测结果 DO
 *
 * @author 管理员
 */
@TableName("public_equip_on_off_check_result")
@KeySequence("public_equip_on_off_check_result_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicEquipOnOffCheckResultDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 所属单位：1-交警；2-公安
     */
    private String affiliatedUnit;
    /**
     * 设备id
     */
    private String equId;
    /**
     * 设备编码
     */
    private String equCode;
    /**
     * 检测类型：1-all；2-ping
     */
    private String checkType;
    /**
     * 设备类型
     */
    private String equType;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 检测时间
     */
    private LocalDateTime occurrenceTime;
    /**
     * 通断状态
     */
    private String pingStatus;
    /**
     * ping次数
     */
    private Integer pingCnt;
    /**
     * 丢包率
     */
    private BigDecimal lossRate;
    /**
     * 视频状态
     */
    private String videoStatus;
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
    private String checkUserId;
    /**
     * 维护人员
     */
    private String checkUserPersonnel;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}