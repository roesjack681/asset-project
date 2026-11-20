package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备维保无法修复信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceUnfixableInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25511")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "无法修复类型：1-施工；2-设备；3-线路", example = "1")
    @ExcelProperty("无法修复类型：1-施工；2-设备；3-线路")
    private String unfixableType;

    @Schema(description = "无法修复原因", example = "不喜欢")
    @ExcelProperty("无法修复原因")
    private String unfixableReason;

    @Schema(description = "施工方联系人", example = "2")
    private String constructionContacts;

    @Schema(description = "联系电话", example = "222")
    private String phone;

    @Schema(description = "预计可维修时间")
    private LocalDateTime estimatedRepairDate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}