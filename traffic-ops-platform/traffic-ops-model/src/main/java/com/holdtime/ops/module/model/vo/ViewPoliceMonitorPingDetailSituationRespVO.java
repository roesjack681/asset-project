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
public class ViewPoliceMonitorPingDetailSituationRespVO {

    @Schema(description = "设备id", example = "9630")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime latestOccurrenceDay;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime previousOccurrenceDay;

    @Schema(description = "ping状态", example = "2")
    @ExcelProperty("ping状态")
    private String latestPingStatus;

    @Schema(description = "ping状态", example = "2")
    @ExcelProperty("ping状态")
    private String previousPingStatus;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("")
    private String finalPingStatus;

}