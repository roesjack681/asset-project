package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 公安监测结果导入近两日状态 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceMonitorResultsImportSituationRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27268")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备id", example = "467")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "设备IP")
    @ExcelProperty("设备IP")
    private String ipAddr;

    @Schema(description = "设备标签")
    @ExcelProperty("设备标签")
    private String equLable;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "实时视频状态", example = "1")
    @ExcelProperty("实时视频状态")
    private String videoStatus;

    @Schema(description = "ping状态", example = "2")
    @ExcelProperty("ping状态")
    private String pingStatus;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}