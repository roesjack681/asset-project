package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "用户 APP - 设备维保进度新增/修改 Request VO")
@Data
public class AppTrafficOpsMaintainProgressSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23990")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "维保主键", example = "11295")
    private String maintenanceId;

    @Schema(description = "维护情况")
    private String maintenance;

    @Schema(description = "维护人员编号", example = "16913")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "维护时间")
    private LocalDateTime maintenanceDate;

    @Schema(description = "维护地址")
    private String maintenanceAdress;

    @Schema(description = "维护经度")
    private String maintenanceLng;

    @Schema(description = "维护纬度")
    private String maintenanceLat;

    @Schema(description = "是否换新")
    private String isChange;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}