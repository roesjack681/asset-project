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
public class ViewPoliceMonitorPingDetailSituationSaveReqVO {

    @Schema(description = "设备id", example = "9630")
    private String equId;

    @Schema(description = "检测时间")
    private LocalDateTime latestOccurrenceDay;

    @Schema(description = "检测时间")
    private LocalDateTime previousOccurrenceDay;

    @Schema(description = "ping状态", example = "2")
    private String latestPingStatus;

    @Schema(description = "ping状态", example = "2")
    private String previousPingStatus;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "不能为空")
    private String finalPingStatus;

}