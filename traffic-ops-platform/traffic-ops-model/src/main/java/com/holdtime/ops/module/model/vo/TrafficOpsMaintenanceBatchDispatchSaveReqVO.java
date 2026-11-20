package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Schema(description = "管理后台 - 设备维保信息新增/修改 Request VO")
@Data
public class TrafficOpsMaintenanceBatchDispatchSaveReqVO {

    @Schema(description = "派单人员编号", example = "10601")
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

    @Schema(description = "维护人员编号")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "紧急程度")
    private String urgencyLevel;

    @Schema(description = "最后处理时间")
    private LocalDateTime deadline;

    @Schema(description = "报修单ID集合")
    private List<String> maintenanceIdList;

}