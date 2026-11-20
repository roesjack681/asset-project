package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



@Schema(description = "管理后台 - 设备信息")
@Data
public class TrafficOpsEquipInfoDTO {
    @Schema(description = "设备ID")
    private String id;

    @Schema(description = "用户类型")
    private String equType;

    @Schema(description = "道路名称")
    private String roadName;

    @Schema(description = "交叉口名称")
    private String intersectionName;

    @Schema(description = "纬度")
    private String lat;

    @Schema(description = "经度")
    private String lng;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "地址")
    private String adress;

    @Schema(description = "杆件编号")
    private String memberBh;
}