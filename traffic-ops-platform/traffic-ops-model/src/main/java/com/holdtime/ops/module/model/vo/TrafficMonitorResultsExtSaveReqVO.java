package com.holdtime.ops.module.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Schema(description = "管理后台 - 设备监测结果新增/修改 Request VO")
@Data
public class TrafficMonitorResultsExtSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4491")
    private String id;

    @NotBlank
    @Schema(description = "设备类型：1-红绿灯;2-电警", example = "1")
    private String equType;

    @NotBlank
    @Schema(description = "IP地址")
    private String ipAddr;

    @NotBlank
    @Schema(description = "方向")
    private String direction;

    @NotBlank
    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @NotNull
    @Schema(description = "检测时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date occurrenceTime;

    @NotNull
    @Schema(description = "状态")
    private String status;


}