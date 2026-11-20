package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - VIEW新增/修改 Request VO")
@Data
public class ViewPoliceMonitorResultsImportSituationSaveReqVO {

    @Schema(description = "设备id", example = "10752")
    private String equId;

    @Schema(description = "检测时间")
    private LocalDateTime latestOccurrenceDay;

    @Schema(description = "检测时间")
    private LocalDateTime previousOccurrenceDay;

    @Schema(description = "ping状态", example = "1")
    private String latestPingStatus;

    @Schema(description = "ping状态", example = "2")
    private String previousPingStatus;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "不能为空")
    private String finalPingStatus;

    @Schema(description = "实时视频状态", example = "2")
    private String latestVideoStatus;

    @Schema(description = "实时视频状态", example = "1")
    private String previousVideoStatus;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "不能为空")
    private String finalVideoStatus;

}