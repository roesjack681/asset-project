package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 监测电警设备统计新增/修改 Request VO")
@Data
public class TrafficDjMonitorStatisticsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28588")
    private String id;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "检测事件", example = "2")
    private String eventType;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "持续在线时长")
    private String cxTime;

    @Schema(description = "平均在线时长")
    private String avgTime;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}