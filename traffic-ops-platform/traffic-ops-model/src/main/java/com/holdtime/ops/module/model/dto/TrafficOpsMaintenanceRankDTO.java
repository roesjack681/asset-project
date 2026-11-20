package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "管理后台 - 维修排名")
@Data
public class TrafficOpsMaintenanceRankDTO {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "完成订单数")
    private Long doneOrderCnt;

    @Schema(description = "未完成订单数")
    private Long undoneOrderCnt;

    @Schema(description = "完成率")
    private String finishRate;
}