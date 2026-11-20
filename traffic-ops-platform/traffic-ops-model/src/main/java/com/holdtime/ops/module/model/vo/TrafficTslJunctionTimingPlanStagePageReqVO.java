package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信控交叉口配时方案阶段信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficTslJunctionTimingPlanStagePageReqVO extends PageParam {

    @Schema(description = "交叉口ID", example = "24432")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "6297")
    private String controllerId;

    @Schema(description = "配时方案ID", example = "6297")
    private String planId;

    @Schema(description = "阶段ID", example = "22966")
    private String stageId;

    @Schema(description = "绿灯时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] greeTime;

    @Schema(description = "最短绿灯")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] minGreeTime;

    @Schema(description = "最长绿灯")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] maxGreeTime;

    @Schema(description = "黄灯时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] yellowTime;

    @Schema(description = "全红时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] allRedTime;

    @Schema(description = "行闪时间")
    private Integer pedFlash;

    @Schema(description = "行人红灯")
    private Integer pedRed;

    @Schema(description = "绿闪时间")
    private Integer greenFlash;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}