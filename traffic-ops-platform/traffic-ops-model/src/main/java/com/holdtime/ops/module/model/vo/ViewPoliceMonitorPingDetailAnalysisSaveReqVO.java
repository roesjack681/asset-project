package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - VIEW新增/修改 Request VO")
@Data
public class ViewPoliceMonitorPingDetailAnalysisSaveReqVO {

    @Schema(description = "设备id", example = "15953")
    private String equId;

    @Schema(description = "设备IP")
    private String ipAddr;

    @Schema(description = "")
    private BigDecimal autoNormalCnt;

    @Schema(description = "")
    private BigDecimal autoAbnormalCnt;

    @Schema(description = "")
    private BigDecimal autoMonitorCnt;

    @Schema(description = "")
    private BigDecimal autoHealthRate;

    @Schema(description = "")
    private BigDecimal normalCnt;

    @Schema(description = "")
    private BigDecimal abnormalCnt;

    @Schema(description = "")
    private BigDecimal monitorCnt;

    @Schema(description = "")
    private BigDecimal healthRate;

}