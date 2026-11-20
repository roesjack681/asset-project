package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备维保无法修复跟进新增/修改 Request VO")
@Data
public class TrafficOpsMaintenanceUnfixableFollowSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28012")
    private String id;

    @Schema(description = "无法修复记录ID", example = "30801")
    private String unfixableId;

    @Schema(description = "协调内容")
    private String coordinationContent;

    @Schema(description = "协调时间")
    private LocalDateTime coordinationDate;

    @Schema(description = "预计修复时间")
    private LocalDateTime estimatedRepairDate;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}