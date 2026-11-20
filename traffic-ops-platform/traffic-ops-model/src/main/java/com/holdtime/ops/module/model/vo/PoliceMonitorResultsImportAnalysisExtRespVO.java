package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 公安监测结果导入分析 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceMonitorResultsImportAnalysisExtRespVO extends PoliceMonitorResultsImportAnalysisRespVO {
    @Schema(description = "设备IP", example = "8624")
    @ExcelProperty(value = "设备IP" , index = 0)
    private String ipAddr;

    @Schema(description = "设备名称")
    @ExcelProperty(value = "设备名称", index = 1)
    private String equName;

    @Schema(description = "Ping状态")
    @ExcelProperty(value = "Ping状态", index = 6)
    private String pingStatus;

    @Schema(description = "实时视频状态")
    @ExcelProperty(value = "实时视频状态", index = 10)
    private String videoStatus;

    @Schema(description = "近两次Ping状态")
    private String finalPingStatus;

    @Schema(description = "近两次视频状态")
    private String finalVideoStatus;

}