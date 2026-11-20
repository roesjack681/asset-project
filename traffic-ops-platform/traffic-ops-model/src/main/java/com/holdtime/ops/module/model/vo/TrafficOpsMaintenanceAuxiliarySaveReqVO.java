package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护栏辅助设备维护详细新增/修改 Request VO")
@Data
public class TrafficOpsMaintenanceAuxiliarySaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4138")
    private String id;

    @Schema(description = "维保主键", example = "1463")
    private String maintenanceId;

    @Schema(description = "护栏主键", example = "21345")
    private String guardrailId;

    @Schema(description = "辅助设备ID", example = "23263")
    private String auxiliaryId;

    @Schema(description = "辅助设备名称", example = "赵六")
    private String auxiliaryName;

    @Schema(description = "数量")
    private Integer auxiliaryNum;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}