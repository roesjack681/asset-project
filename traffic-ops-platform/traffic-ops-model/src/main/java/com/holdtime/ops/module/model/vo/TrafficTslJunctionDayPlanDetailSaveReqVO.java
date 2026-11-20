package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口日方案详细信息新增/修改 Request VO")
@Data
public class TrafficTslJunctionDayPlanDetailSaveReqVO {

    @Schema(description = "主键(交叉口ID+日方案ID+详细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30249")
    private String id;

    @Schema(description = "详细ID", example = "28146")
    private String segmentId;

    @Schema(description = "日方案ID", example = "4771")
    private String dayPlansId;

    @Schema(description = "交叉口ID", example = "30129")
    private String junctionId;

    @Schema(description = "小时")
    private Integer planHour;

    @Schema(description = "分钟")
    private Integer planMinute;

    @Schema(description = "配时方案号", example = "19694")
    private String planId;

    @Schema(description = "感应方案号", example = "17914")
    private String actuId;

    @Schema(description = "相序方案号", example = "29009")
    private String stageListId;

    @Schema(description = "相位差")
    private Integer phaseDifference;

    @Schema(description = "周期")
    private Integer cycleTime;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}