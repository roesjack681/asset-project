package com.holdtime.ops.module.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 维修排名")
@Data
public class PoliceMonitorPingHourStatusDTO {
    @Schema(description = "检测时间")
    private String monitorHour;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "ping次数")
    private Integer pingCnt;

    @Schema(description = "丢包率")
    private BigDecimal lossRate;
}
