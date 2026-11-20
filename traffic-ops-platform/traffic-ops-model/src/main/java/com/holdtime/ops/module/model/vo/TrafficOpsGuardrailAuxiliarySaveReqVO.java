package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护栏辅助设备新增/修改 Request VO")
@Data
public class TrafficOpsGuardrailAuxiliarySaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11985")
    private String id;

    @Schema(description = "护栏主键", example = "1822")
    private String guardrailId;

    @Schema(description = "维保主键", example = "30047")
    private String maintenanceId;

    @Schema(description = "护栏维护主键", example = "7771")
    private String manceGuardrailId;

    @Schema(description = "辅助设备名称", example = "王五")
    private String auxiliaryName;

    @Schema(description = "数量")
    private Integer auxiliaryNum;

    @Schema(description = "图片")
    private String auxiliaryImg;

    @Schema(description = "状态：安装；拆除", example = "2")
    private String status;

    @Schema(description = "安装时间")
    private LocalDateTime installTime;

    @Schema(description = "拆除时间")
    private LocalDateTime demolishTime;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}