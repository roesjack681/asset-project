package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 护栏保险信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsGuardrailInsuranceRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25281")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "护栏维护主键", example = "15916")
    @ExcelProperty("护栏维护主键")
    private String manceGuardrailId;

    @Schema(description = "车牌号")
    @ExcelProperty("车牌号")
    private String vehicleNum;

    @Schema(description = "保险公司名称")
    @ExcelProperty("保险公司名称")
    private String insuranceCompany;

    @Schema(description = "保单编号")
    @ExcelProperty("保单编号")
    private String insurancePolicy;

    @Schema(description = "赔付金额")
    @ExcelProperty("赔付金额")
    private BigDecimal payMoney;

    @Schema(description = "赔付护栏产品编号", example = "19053")
    @ExcelProperty("赔付护栏产品编号")
    private String productId;

    @Schema(description = "赔付片数")
    @ExcelProperty("赔付片数")
    private Integer pieces;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}