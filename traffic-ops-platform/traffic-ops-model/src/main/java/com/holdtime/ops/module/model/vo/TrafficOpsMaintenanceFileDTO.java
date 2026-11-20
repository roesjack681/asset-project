package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 设备维保材料信息新增/修改 Request VO")
@Data
public class TrafficOpsMaintenanceFileDTO {

    @Schema(description = "材料类型：图片；音频；视频", example = "2")
    private String fileType;

    @Schema(description = "材料url", example = "443")
    private String fileUrl;
}