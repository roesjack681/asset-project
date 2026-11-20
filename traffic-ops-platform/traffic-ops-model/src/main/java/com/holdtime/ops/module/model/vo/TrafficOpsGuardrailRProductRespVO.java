package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 护栏关联产品信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsGuardrailRProductRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27317")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "产品编号", example = "21187")
    @ExcelProperty("产品编号")
    private String productId;

    @Schema(description = "护栏编号", example = "14035")
    @ExcelProperty("护栏编号")
    private String guardrailId;

    @Schema(description = "片数")
    @ExcelProperty("片数")
    private Integer pieces;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}