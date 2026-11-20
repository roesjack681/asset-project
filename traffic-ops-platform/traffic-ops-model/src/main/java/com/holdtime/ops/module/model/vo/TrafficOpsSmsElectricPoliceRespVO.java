package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "微信端 - 短信告警电警 Response VO")
@Data
public class TrafficOpsSmsElectricPoliceRespVO {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "设备地址")
    private String adress;

    @Schema(description = "IP地址")
    private String ipAddr;

}