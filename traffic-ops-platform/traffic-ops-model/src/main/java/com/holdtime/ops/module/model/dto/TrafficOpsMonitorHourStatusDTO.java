package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 维修排名")
@Data
public class TrafficOpsMonitorHourStatusDTO {
    private String monitorDay;

    private String status;
}
