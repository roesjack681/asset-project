package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;

@Schema(description = "管理后台 - VIEW分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewPoliceMonitorPingDetailAnalysisPageReqVO extends PageParam {

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