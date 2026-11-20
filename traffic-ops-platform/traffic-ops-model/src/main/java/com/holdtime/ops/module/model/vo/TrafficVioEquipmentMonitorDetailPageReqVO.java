package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 集指设备检测记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficVioEquipmentMonitorDetailPageReqVO extends PageParam {

    @Schema(description = "检测时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] occurrenceTime;

    @Schema(description = "设备id", example = "3839")
    private String equId;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "设备IP")
    private String ipAddr;

    @Schema(description = "ping状态", example = "2")
    private String pingStatus;

    @Schema(description = "实时视频状态", example = "1")
    private String videoStatus;

    @Schema(description = "无违法次数")
    private Integer noSurveilCnt;

    @Schema(description = "有效违法数")
    private Integer validSurveilCnt;

    @Schema(description = "无效违法数")
    private Integer invalidSurveilCnt;

    @Schema(description = "操作记录")
    private String operateMark;

    @Schema(description = "操作描述")
    private String operateDesc;

    @Schema(description = "操作时间")
    private LocalDateTime operateDate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}