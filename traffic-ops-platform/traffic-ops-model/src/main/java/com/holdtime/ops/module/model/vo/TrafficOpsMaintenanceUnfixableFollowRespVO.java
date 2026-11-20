package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备维保无法修复跟进 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceUnfixableFollowRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28012")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "无法修复记录ID", example = "30801")
    @ExcelProperty("无法修复记录ID")
    private String unfixableId;

    @Schema(description = "协调内容")
    @ExcelProperty("协调内容")
    private String coordinationContent;

    @Schema(description = "协调时间")
    @ExcelProperty("协调时间")
    private LocalDateTime coordinationDate;

    @Schema(description = "预计修复时间")
    @ExcelProperty("预计修复时间")
    private LocalDateTime estimatedRepairDate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    private List<TrafficOpsMaintenanceFileDTO> fileDTOList;

}