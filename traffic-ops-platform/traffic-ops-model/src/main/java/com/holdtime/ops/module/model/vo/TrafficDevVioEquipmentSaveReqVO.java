package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 违法抓拍设备新增/修改 Request VO")
@Data
public class TrafficDevVioEquipmentSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15739")
    private String id;

    @Schema(description = "设备id", example = "32373")
    private String equId;

    @Schema(description = "设备类型")
    private String sblx;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "设备名称")
    private String sbmc;

    @Schema(description = "所在道路")
    private String szdd;

}