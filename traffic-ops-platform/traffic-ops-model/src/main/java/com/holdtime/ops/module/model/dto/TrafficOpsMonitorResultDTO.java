package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Schema(description = "管理后台 - 维修排名")
@Data
public class TrafficOpsMonitorResultDTO {
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

    @Schema(description = "正常数")
    private BigDecimal normalCnt;

    @Schema(description = "异常数")
    private BigDecimal abnormalCnt;

    @Schema(description = "检测总天数")
    private BigDecimal monitorSumDays;

    @Schema(description = "检测总次数")
    private String monitorSumCnt;

    @Schema(description = "健康率")
    private String healthRate;
}