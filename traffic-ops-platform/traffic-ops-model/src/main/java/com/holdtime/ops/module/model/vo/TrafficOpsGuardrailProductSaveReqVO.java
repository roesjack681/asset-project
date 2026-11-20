package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护栏产品信息新增/修改 Request VO")
@Data
public class TrafficOpsGuardrailProductSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5992")
    private String id;

    @Schema(description = "高度")
    private String hight;

    @Schema(description = "长度")
    private BigDecimal length;

    @Schema(description = "材质")
    private String material;

    @Schema(description = "厂家")
    private String manufacturer;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "底座")
    private String pedestal;

}