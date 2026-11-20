package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信控交叉口配时方案信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficTslJunctionTimingPlanRespVO {

    @Schema(description = "主键(交叉口ID+配时方案ID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "32334")
    @ExcelProperty("主键(交叉口ID+配时方案ID)")
    private String id;

    @Schema(description = "配时方案ID", example = "8326")
    @ExcelProperty("配时方案ID")
    private String planId;

    @Schema(description = "交叉口ID", example = "28901")
    @ExcelProperty("交叉口ID")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "16731")
    @ExcelProperty("交叉口控制id")
    private String controllerId;

    @Schema(description = "标识")
    @ExcelProperty("标识")
    private String flag;

    @Schema(description = "相序方案号", example = "7479")
    @ExcelProperty("相序方案号")
    private String stageListId;

    @Schema(description = "方案数量", example = "21723")
    @ExcelProperty("方案数量")
    private Integer stageCount;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direct;

    @Schema(description = "周期")
    @ExcelProperty("周期")
    private Integer cycleTime;

    @Schema(description = "相位差")
    @ExcelProperty("相位差")
    private Integer phaseDifference;

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