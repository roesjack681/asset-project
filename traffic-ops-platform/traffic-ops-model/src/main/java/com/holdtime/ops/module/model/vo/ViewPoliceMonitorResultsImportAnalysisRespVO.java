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
public class ViewPoliceMonitorResultsImportAnalysisRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "561")
    @ExcelProperty("主键")
    private String equId;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "设备标签")
    @ExcelProperty("设备标签")
    private String equLable;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal videoNormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal videoAbnormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal videoHealthRate;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal pingNormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal pingAbnormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal pingHealthRate;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal monitorSumCnt;

}