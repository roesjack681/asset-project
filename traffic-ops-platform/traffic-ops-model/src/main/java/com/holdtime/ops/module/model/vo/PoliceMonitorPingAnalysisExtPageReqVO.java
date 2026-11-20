package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 电警通断检测管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PoliceMonitorPingAnalysisExtPageReqVO extends PoliceMonitorPingAnalysisPageReqVO {
    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "设备名称", example = "4715")
    private String equName;

    @Schema(description = "设备标签")
    private List<String> equLableList;

    @Schema(description = "行政区域")
    private List<String> unitList;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "近两次ping状态")
    private String finalPingStatus;
}