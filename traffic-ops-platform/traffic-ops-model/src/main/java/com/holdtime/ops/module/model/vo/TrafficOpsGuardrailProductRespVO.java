package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 护栏产品信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsGuardrailProductRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5992")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "高度")
    @ExcelProperty("高度")
    private String hight;

    @Schema(description = "长度")
    @ExcelProperty("长度")
    private BigDecimal length;

    @Schema(description = "材质")
    @ExcelProperty("材质")
    private String material;

    @Schema(description = "厂家")
    @ExcelProperty("厂家")
    private String manufacturer;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String model;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "底座")
    @ExcelProperty("底座")
    private String pedestal;

}