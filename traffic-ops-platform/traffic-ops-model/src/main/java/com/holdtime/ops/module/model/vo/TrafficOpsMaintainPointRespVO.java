package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 维修点位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintainPointRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28295")
    private String id;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "设备ID", example = "2")
    private String equId;

    @Schema(description = "报修时间", example = "芋艿")
    private String faultDate;

    @Schema(description = "问题", example = "芋艿")
    private String problem;

    @Schema(description = "故障类型", example = "芋艿")
    private String faultType;

    @Schema(description = "紧急程度", example = "芋艿")
    private String rgencyLevel;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "维护人员")
    private String maintenancePersonnel;

    @Schema(description = "设备IP", example = "芋艿")
    private String ipAddr;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "电警类型", example = "芋艿")
    private String djType;

    @Schema(description = "设备标签", example = "芋艿")
    private String equ_lable;

}