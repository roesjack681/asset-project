package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 护栏辅助设备 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsGuardrailAuxiliaryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11985")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "护栏主键", example = "1822")
    @ExcelProperty("护栏主键")
    private String guardrailId;

    @Schema(description = "维保主键", example = "30047")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "护栏维护主键", example = "7771")
    @ExcelProperty("护栏维护主键")
    private String manceGuardrailId;

    @Schema(description = "辅助设备名称", example = "王五")
    @ExcelProperty("辅助设备名称")
    private String auxiliaryName;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private Integer auxiliaryNum;

    @Schema(description = "图片")
    @ExcelProperty("图片")
    private String auxiliaryImg;

    @Schema(description = "状态：安装；拆除", example = "2")
    @ExcelProperty("状态：安装；拆除")
    private String status;

    @Schema(description = "安装时间")
    @ExcelProperty("安装时间")
    private LocalDateTime installTime;

    @Schema(description = "拆除时间")
    @ExcelProperty("拆除时间")
    private LocalDateTime demolishTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}