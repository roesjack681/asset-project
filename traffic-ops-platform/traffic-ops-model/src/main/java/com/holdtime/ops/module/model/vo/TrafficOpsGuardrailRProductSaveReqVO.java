package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护栏关联产品信息新增/修改 Request VO")
@Data
public class TrafficOpsGuardrailRProductSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27317")
    private String id;

    @Schema(description = "产品编号", example = "21187")
    private String productId;

    @Schema(description = "护栏编号", example = "14035")
    private String guardrailId;

    @Schema(description = "片数")
    private Integer pieces;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}