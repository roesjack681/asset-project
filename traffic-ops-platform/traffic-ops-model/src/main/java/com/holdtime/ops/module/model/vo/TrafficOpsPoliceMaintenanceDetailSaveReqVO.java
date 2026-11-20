package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 警用装备报修详情信息新增/修改 Request VO")
@Data
public class TrafficOpsPoliceMaintenanceDetailSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32277")
    private String id;

    @Schema(description = "报修编号", example = "10158")
    private String maintenanceId;

    @Schema(description = "装备编号", example = "29215")
    private String equId;

    @Schema(description = "问题描述")
    private String problem;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}