package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 警用装备资产信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsPoliceEquipmentOptionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10555")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "类型", example = "2")
    @ExcelProperty("类型")
    private String equType;

    @Schema(description = "PSN")
    @ExcelProperty("PSN")
    private String psn;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String model;
}