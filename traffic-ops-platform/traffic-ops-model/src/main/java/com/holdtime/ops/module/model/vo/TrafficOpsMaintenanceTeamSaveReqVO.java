package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备维保组员信息新增/修改 Request VO")
@Data
public class TrafficOpsMaintenanceTeamSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "14850")
    private String id;

    @Schema(description = "维保主键", example = "2423")
    private String maintenanceId;

    @Schema(description = "维保进度id", example = "10134")
    private String progressId;

    @Schema(description = "组员人员编号", example = "13546")
    private String maintenanceUserId;

    @Schema(description = "组员姓名")
    private String maintenancePersonnel;

    @Schema(description = "组员手机号")
    private String maintenancePhone;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}