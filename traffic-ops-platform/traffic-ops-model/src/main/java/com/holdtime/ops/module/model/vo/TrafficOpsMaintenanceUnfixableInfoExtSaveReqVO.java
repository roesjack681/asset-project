package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 设备维保无法修复信息新增/修改 Request VO")
@Data
public class TrafficOpsMaintenanceUnfixableInfoExtSaveReqVO extends TrafficOpsMaintenanceUnfixableInfoSaveReqVO {
    @Schema(description = "协调内容")
    private String coordinationContent;

    @Schema(description = "协调时间")
    private LocalDateTime coordinationDate;

    @Schema(description = "预计修复时间")
    private LocalDateTime estimatedRepairDate;

    @Schema(description = "附件信息")
    private List<TrafficOpsMaintenanceFileDTO> fileInfoList;
}