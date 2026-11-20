package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口日方案信息新增/修改 Request VO")
@Data
public class TrafficTslJunctionDayPlanInfoSaveReqVO {

    @Schema(description = "主键(交叉口ID+日方案ID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "15231")
    private String id;

    @Schema(description = "日方案ID", example = "24482")
    private String dayPlansId;

    @Schema(description = "交叉口ID", example = "29441")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "27881")
    private String controllerId;

    @Schema(description = "标识")
    private String flag;

    @Schema(description = "假期方案是否使用")
    private String holidayPlanUse;

    @Schema(description = "方案数量", example = "28258")
    private Integer segmentCount;

    @Schema(description = "是否使用")
    private String isUse;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}