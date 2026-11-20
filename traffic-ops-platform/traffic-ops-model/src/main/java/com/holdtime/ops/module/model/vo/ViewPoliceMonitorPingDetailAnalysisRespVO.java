package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - VIEW Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViewPoliceMonitorPingDetailAnalysisRespVO {

    @Schema(description = "设备id", example = "15953")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "设备IP")
    @ExcelProperty("设备IP")
    private String ipAddr;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal autoNormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal autoAbnormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal autoMonitorCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal autoHealthRate;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal normalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal abnormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal monitorCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal healthRate;

}