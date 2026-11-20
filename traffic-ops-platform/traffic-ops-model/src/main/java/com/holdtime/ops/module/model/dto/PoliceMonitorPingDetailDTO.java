package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Schema(description = "管理后台 - 公安设备通断检测详情")
@Data
public class PoliceMonitorPingDetailDTO {
    @Schema(description = "设备ID")
    private String equId;

    @Schema(description = "设备名称")
    private String equName;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测时间")
    private String monitorDay;

    @Schema(description = "日检查次数")
    private BigDecimal dayMonitorCnt;

    @Schema(description = "时刻状态")
    private List<PoliceMonitorPingHourStatusDTO> hourStatusResultList;
}