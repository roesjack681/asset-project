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
public class ViewPoliceMonitorResultsImportAnalysisPageReqVO extends PageParam {

    @Schema(description = "主键", example = "561")
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