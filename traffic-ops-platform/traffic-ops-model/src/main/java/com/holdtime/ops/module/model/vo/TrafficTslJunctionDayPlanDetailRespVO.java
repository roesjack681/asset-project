package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信控交叉口日方案详细信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficTslJunctionDayPlanDetailRespVO {

    @Schema(description = "主键(交叉口ID+日方案ID+详细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30249")
    @ExcelProperty("主键(交叉口ID+日方案ID+详细ID")
    private String id;

    @Schema(description = "详细ID", example = "28146")
    @ExcelProperty("详细ID")
    private String segmentId;

    @Schema(description = "日方案ID", example = "4771")
    @ExcelProperty("日方案ID")
    private String dayPlansId;

    @Schema(description = "交叉口ID", example = "30129")
    @ExcelProperty("交叉口ID")
    private String junctionId;

    @Schema(description = "小时")
    @ExcelProperty("小时")
    private Integer planHour;

    @Schema(description = "分钟")
    @ExcelProperty("分钟")
    private Integer planMinute;

    @Schema(description = "配时方案号", example = "19694")
    @ExcelProperty("配时方案号")
    private String planId;

    @Schema(description = "感应方案号", example = "17914")
    @ExcelProperty("感应方案号")
    private String actuId;

    @Schema(description = "相序方案号", example = "29009")
    @ExcelProperty("相序方案号")
    private String stageListId;

    @Schema(description = "相位差")
    @ExcelProperty("相位差")
    private Integer phaseDifference;

    @Schema(description = "周期")
    @ExcelProperty("周期")
    private Integer cycleTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}