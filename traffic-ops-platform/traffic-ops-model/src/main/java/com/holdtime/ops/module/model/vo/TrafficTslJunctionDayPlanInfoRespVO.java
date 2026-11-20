package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信控交叉口日方案信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficTslJunctionDayPlanInfoRespVO {

    @Schema(description = "主键(交叉口ID+日方案ID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "15231")
    @ExcelProperty("主键(交叉口ID+日方案ID)")
    private String id;

    @Schema(description = "日方案ID", example = "24482")
    @ExcelProperty("日方案ID")
    private String dayPlansId;

    @Schema(description = "交叉口ID", example = "29441")
    @ExcelProperty("交叉口ID")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "27881")
    @ExcelProperty("交叉口控制id")
    private String controllerId;

    @Schema(description = "标识")
    @ExcelProperty("标识")
    private String flag;

    @Schema(description = "假期方案是否使用")
    @ExcelProperty("假期方案是否使用")
    private String holidayPlanUse;

    @Schema(description = "方案数量", example = "28258")
    @ExcelProperty("方案数量")
    private Integer segmentCount;

    @Schema(description = "是否使用")
    @ExcelProperty("是否使用")
    private String isUse;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}