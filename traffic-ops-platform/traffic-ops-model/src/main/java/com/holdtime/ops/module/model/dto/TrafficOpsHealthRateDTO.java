package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;


@Schema(description = "管理后台 - 维修排名")
@Data
public class TrafficOpsHealthRateDTO {

    @Schema(description = "分析日期")
    private String analysisTime;

    @Schema(description = "正常数")
    private Integer normalCnt;

    @Schema(description = "总数")
    private Integer sumCnt;

    @Schema(description = "健康率")
    private String healthRate;
}