package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口配时方案信息新增/修改 Request VO")
@Data
public class TrafficTslJunctionTimingPlanSaveReqVO {

    @Schema(description = "主键(交叉口ID+配时方案ID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "32334")
    private String id;

    @Schema(description = "配时方案ID", example = "8326")
    private String planId;

    @Schema(description = "交叉口ID", example = "28901")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "16731")
    private String controllerId;

    @Schema(description = "标识")
    private String flag;

    @Schema(description = "相序方案号", example = "7479")
    private String stageListId;

    @Schema(description = "方案数量", example = "21723")
    private Integer stageCount;

    @Schema(description = "方向")
    private String direct;

    @Schema(description = "周期")
    private Integer cycleTime;

    @Schema(description = "相位差")
    private Integer phaseDifference;

    @Schema(description = "是否使用")
    private String isUse;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}