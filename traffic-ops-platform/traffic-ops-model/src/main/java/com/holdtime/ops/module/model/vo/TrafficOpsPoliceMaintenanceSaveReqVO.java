package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 警用装备报修信息新增/修改 Request VO")
@Data
public class TrafficOpsPoliceMaintenanceSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5133")
    private String id;

    @Schema(description = "上报人员")
    private String reportPersonnel;

    @Schema(description = "上报人员编号", example = "17254")
    private String reportUserId;

    @Schema(description = "上报时间")
    private LocalDateTime reportTime;

    @Schema(description = "上报人员部门编号", example = "26748")
    private String reportDeptId;

    @Schema(description = "上报人员部门")
    private String reportDept;

    @Schema(description = "审核人员编号")
    private String auditorBh;

    @Schema(description = "审核时间")
    private LocalDateTime auditDate;

    @Schema(description = "审核人")
    private String auditor;

    @Schema(description = "审核意见")
    private String auditOpinions;

    @Schema(description = "维修人编号", example = "27103")
    private String maintenanceUserId;

    @Schema(description = "维修人")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "维护时间")
    private LocalDateTime maintenanceDate;

    @Schema(description = "维护建议")
    private String suggestion;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}