package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 违法抓拍设备 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficDevVioEquipmentRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15739")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备id", example = "32373")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "设备类型")
    @ExcelProperty("设备类型")
    private String sblx;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String sbbh;

    @Schema(description = "设备名称")
    @ExcelProperty("设备名称")
    private String sbmc;

    @Schema(description = "所在道路")
    @ExcelProperty("所在道路")
    private String szdd;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}