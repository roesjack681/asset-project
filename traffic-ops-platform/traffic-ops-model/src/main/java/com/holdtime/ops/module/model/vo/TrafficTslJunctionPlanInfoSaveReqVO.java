package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口方案信息新增/修改 Request VO")
@Data
public class TrafficTslJunctionPlanInfoSaveReqVO {

    @Schema(description = "交叉口ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30767")
    private String id;

    @Schema(description = "交叉口控制id", example = "7604")
    private String controllerId;

    @Schema(description = "周方案")
    private String weekPlans;

    @Schema(description = "假期开始时间")
    private LocalDateTime holidaysStartDate;

    @Schema(description = "假期结束时间")
    private LocalDateTime holidaysEndDate;

    @Schema(description = "假期方案")
    private String holidayPlans;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}