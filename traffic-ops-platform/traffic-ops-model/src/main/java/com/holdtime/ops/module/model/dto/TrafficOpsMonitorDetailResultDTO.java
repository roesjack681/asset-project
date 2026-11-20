package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Schema(description = "管理后台 - 维修排名")
@Data
public class TrafficOpsMonitorDetailResultDTO {
    @Schema(description = "用户ID")
    private String equId;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "道路名称")
    private String roadName;

    @Schema(description = "交叉口名称")
    private String intersectionName;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "检测事件")
    private String eventType;

    @Schema(description = "检测时间")
    private String monitorDay;

    @Schema(description = "日检查次数")
    private BigDecimal dayMonitorCnt;

    @Schema(description = "时刻状态")
    private List<TrafficOpsMonitorHourStatusDTO> hourStatusResultList;
}