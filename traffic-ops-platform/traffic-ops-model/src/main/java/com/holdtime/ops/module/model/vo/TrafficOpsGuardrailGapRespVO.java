package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 护栏开口信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsGuardrailGapRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7848")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "护栏主键", example = "10933")
    @ExcelProperty("护栏主键")
    private String guardrailId;

    @Schema(description = "维保主键", example = "15132")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "护栏维护主键", example = "5415")
    @ExcelProperty("护栏维护主键")
    private String manceGuardrailId;

    @Schema(description = "开口名称", example = "芋艿")
    @ExcelProperty("开口名称")
    private String gapName;

    @Schema(description = "开口类型", example = "2")
    @ExcelProperty("开口类型")
    private String gapType;

    @Schema(description = "GPS定位-经度")
    @ExcelProperty("GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    @ExcelProperty("GPS定位-纬度")
    private String lat;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}