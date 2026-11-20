package com.holdtime.ops.module.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(description = "管理后台 - 监测电警设备统计新增/修改 Request VO")
@Data
public class TrafficDjMonitorStatisticsExtSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28588")
    private String id;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "检测事件", example = "2")
    private String eventType;

    @Schema(description = "检测时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date occurrenceTime;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "持续在线时长")
    private String cxTime;

    @Schema(description = "平均在线时长")
    private String avgTime;

}