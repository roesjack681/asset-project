package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信控交叉口配时方案阶段信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficTslJunctionTimingPlanStageRespVO {

    @Schema(description = "主键(交叉口ID+配时方案ID+阶段ID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "4729")
    @ExcelProperty("主键(交叉口ID+配时方案ID+阶段ID)")
    private String id;

    @Schema(description = "交叉口ID", example = "24432")
    @ExcelProperty("交叉口ID")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "6297")
    @ExcelProperty("交叉口控制id")
    private String controllerId;

    @Schema(description = "配时方案ID", example = "6297")
    private String planId;

    @Schema(description = "阶段ID", example = "22966")
    @ExcelProperty("阶段ID")
    private String stageId;

    @Schema(description = "绿灯时间")
    @ExcelProperty("绿灯时间")
    private Integer greeTime;

    @Schema(description = "最短绿灯")
    @ExcelProperty("最短绿灯")
    private Integer minGreeTime;

    @Schema(description = "最长绿灯")
    @ExcelProperty("最长绿灯")
    private Integer maxGreeTime;

    @Schema(description = "黄灯时间")
    @ExcelProperty("黄灯时间")
    private Integer yellowTime;

    @Schema(description = "全红时间")
    @ExcelProperty("全红时间")
    private Integer allRedTime;

    @Schema(description = "行闪时间")
    @ExcelProperty("行闪时间")
    private Integer pedFlash;

    @Schema(description = "行人红灯")
    @ExcelProperty("行人红灯")
    private Integer pedRed;

    @Schema(description = "绿闪时间")
    @ExcelProperty("绿闪时间")
    private Integer greenFlash;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}