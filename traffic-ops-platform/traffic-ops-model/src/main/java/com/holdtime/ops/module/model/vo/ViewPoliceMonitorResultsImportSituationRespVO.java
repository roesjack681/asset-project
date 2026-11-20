package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - VIEW Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViewPoliceMonitorResultsImportSituationRespVO {

    @Schema(description = "设备id", example = "10752")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime latestOccurrenceDay;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime previousOccurrenceDay;

    @Schema(description = "ping状态", example = "1")
    @ExcelProperty("ping状态")
    private String latestPingStatus;

    @Schema(description = "ping状态", example = "2")
    @ExcelProperty("ping状态")
    private String previousPingStatus;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("")
    private String finalPingStatus;

    @Schema(description = "实时视频状态", example = "2")
    @ExcelProperty("实时视频状态")
    private String latestVideoStatus;

    @Schema(description = "实时视频状态", example = "1")
    @ExcelProperty("实时视频状态")
    private String previousVideoStatus;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("")
    private String finalVideoStatus;

}