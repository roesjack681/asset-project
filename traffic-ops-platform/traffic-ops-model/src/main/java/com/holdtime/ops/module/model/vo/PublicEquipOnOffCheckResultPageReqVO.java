package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备通断测结果分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PublicEquipOnOffCheckResultPageReqVO extends PageParam {

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "设备id", example = "7048")
    private String equId;

    @Schema(description = "设备编码")
    private String equCode;

    @Schema(description = "检测类型：1-all；2-ping", example = "2")
    private String checkType;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "检测时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] occurrenceTime;

    @Schema(description = "通断状态", example = "2")
    private String pingStatus;

    @Schema(description = "ping次数")
    @ExcelProperty("ping次数")
    private Integer pingCnt;

    @Schema(description = "丢包率")
    @ExcelProperty("丢包率")
    private BigDecimal lossRate;

    @Schema(description = "视频状态", example = "2")
    private String videoStatus;

    @Schema(description = "检测结果图片")
    private String resultImg;

    @Schema(description = "是否为工单")
    private String isRepairOrder;

    @Schema(description = "维保主键", example = "7976")
    private String maintenanceId;

    @Schema(description = "维护人员编号", example = "4082")
    private String checkUserId;

    @Schema(description = "维护人员")
    private String checkUserPersonnel;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}