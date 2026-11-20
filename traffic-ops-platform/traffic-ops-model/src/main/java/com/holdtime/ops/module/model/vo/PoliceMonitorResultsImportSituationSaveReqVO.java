package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 公安监测结果导入近两日状态新增/修改 Request VO")
@Data
public class PoliceMonitorResultsImportSituationSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27268")
    private String id;

    @Schema(description = "设备id", example = "467")
    private String equId;

    @Schema(description = "设备IP")
    private String ipAddr;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "实时视频状态", example = "1")
    private String videoStatus;

    @Schema(description = "ping状态", example = "2")
    private String pingStatus;

}