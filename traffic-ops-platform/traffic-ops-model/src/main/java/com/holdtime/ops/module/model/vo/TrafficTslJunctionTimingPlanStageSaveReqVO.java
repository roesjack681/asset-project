package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口配时方案阶段信息新增/修改 Request VO")
@Data
public class TrafficTslJunctionTimingPlanStageSaveReqVO {

    @Schema(description = "主键(交叉口ID+配时方案ID+阶段ID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "4729")
    private String id;

    @Schema(description = "交叉口ID", example = "24432")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "6297")
    private String controllerId;

    @Schema(description = "配时方案ID", example = "6297")
    private String planId;

    @Schema(description = "阶段ID", example = "22966")
    private String stageId;

    @Schema(description = "绿灯时间")
    private Integer greeTime;

    @Schema(description = "最短绿灯")
    private Integer minGreeTime;

    @Schema(description = "最长绿灯")
    private Integer maxGreeTime;

    @Schema(description = "黄灯时间")
    private Integer yellowTime;

    @Schema(description = "全红时间")
    private Integer allRedTime;

    @Schema(description = "行闪时间")
    private Integer pedFlash;

    @Schema(description = "行人红灯")
    private Integer pedRed;

    @Schema(description = "绿闪时间")
    private Integer greenFlash;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}