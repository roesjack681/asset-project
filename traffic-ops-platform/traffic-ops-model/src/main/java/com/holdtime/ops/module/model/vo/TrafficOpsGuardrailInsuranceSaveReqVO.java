package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护栏保险信息新增/修改 Request VO")
@Data
public class TrafficOpsGuardrailInsuranceSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25281")
    private String id;

    @Schema(description = "护栏维护主键", example = "15916")
    private String manceGuardrailId;

    @Schema(description = "车牌号")
    private String vehicleNum;

    @Schema(description = "保险公司名称")
    private String insuranceCompany;

    @Schema(description = "保单编号")
    private String insurancePolicy;

    @Schema(description = "赔付金额")
    private BigDecimal payMoney;

    @Schema(description = "赔付护栏产品编号", example = "19053")
    private String productId;

    @Schema(description = "赔付片数")
    private Integer pieces;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}