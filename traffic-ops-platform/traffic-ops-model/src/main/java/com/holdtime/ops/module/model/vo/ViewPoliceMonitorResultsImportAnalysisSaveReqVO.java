package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - VIEW新增/修改 Request VO")
@Data
public class ViewPoliceMonitorResultsImportAnalysisSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "561")
    private String equId;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "")
    private BigDecimal videoNormalCnt;

    @Schema(description = "")
    private BigDecimal videoAbnormalCnt;

    @Schema(description = "")
    private BigDecimal videoHealthRate;

    @Schema(description = "")
    private BigDecimal pingNormalCnt;

    @Schema(description = "")
    private BigDecimal pingAbnormalCnt;

    @Schema(description = "")
    private BigDecimal pingHealthRate;

    @Schema(description = "")
    private BigDecimal monitorSumCnt;

}