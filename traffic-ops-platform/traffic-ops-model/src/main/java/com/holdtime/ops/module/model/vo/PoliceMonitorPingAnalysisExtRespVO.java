package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 电警通断检测管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceMonitorPingAnalysisExtRespVO extends PoliceMonitorPingAnalysisRespVO {
    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "设备名称")
    @ExcelProperty("设备名称")
    private String equName;

    /**
     * 检测时间
     */
    @Schema(description = "最新检测时间")
    private LocalDateTime latestOccurrenceDay;

    @Schema(description = "Ping状态")
    @ExcelProperty("Ping状态")
    private String pingStatus;

    @Schema(description = "近两次Ping状态")
    @ExcelProperty("近两次Ping状态")
    private String finalPingStatus;
}