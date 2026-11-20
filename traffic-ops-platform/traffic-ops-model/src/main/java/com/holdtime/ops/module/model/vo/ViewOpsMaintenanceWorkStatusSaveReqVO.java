package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - VIEW新增/修改 Request VO")
@Data
public class ViewOpsMaintenanceWorkStatusSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12261")
    private String id;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "设备编号", example = "7256")
    private String equId;

    @Schema(description = "设备地址")
    private String equAddr;

    @Schema(description = "设备经度")
    private String equLng;

    @Schema(description = "设备纬度")
    private String equLat;

    @Schema(description = "存在问题")
    private String problem;

    @Schema(description = "故障分类", example = "1")
    private String faultType;

    @Schema(description = "故障时间")
    private LocalDateTime faultDate;

    @Schema(description = "上报人员")
    private String reportPersonnel;

    @Schema(description = "上报人员编号", example = "2617")
    private String reportUserId;

    @Schema(description = "上报时间")
    private LocalDateTime reportTime;

    @Schema(description = "上报人员部门编号", example = "31740")
    private String reportDeptId;

    @Schema(description = "上报人员部门")
    private String reportDept;

    @Schema(description = "派单人员编号", example = "15643")
    private String dispatchUserId;

    @Schema(description = "派单人")
    private String dispatch;

    @Schema(description = "派单时间")
    private LocalDateTime dispatchTime;

    @Schema(description = "派单地址")
    private String dispatchAdress;

    @Schema(description = "派单经度")
    private String dispatchLng;

    @Schema(description = "派单纬度")
    private String dispatchLat;

    @Schema(description = "接单地址")
    private String receiveAdress;

    @Schema(description = "接单经度")
    private String receiveLng;

    @Schema(description = "接单纬度")
    private String receiveLat;

    @Schema(description = "接单时间")
    private LocalDateTime receiveDate;

    @Schema(description = "维护建议")
    private String suggestion;

    @Schema(description = "取消人员")
    private String cancelPersonnel;

    @Schema(description = "取消人员编号", example = "18626")
    private String cancelUserId;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因", example = "不好")
    private String cancelReason;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "紧急程度")
    private String urgencyLevel;

    @Schema(description = "最后处理时间")
    private LocalDateTime deadline;

    @Schema(description = "组员人员编号", example = "1660")
    private String maintenanceUserId;

    @Schema(description = "组员姓名")
    private String maintenancePersonnel;

}