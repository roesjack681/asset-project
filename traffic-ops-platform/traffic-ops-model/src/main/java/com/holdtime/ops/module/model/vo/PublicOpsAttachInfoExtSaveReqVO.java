package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产附件信息新增/修改 Request VO")
@Data
public class PublicOpsAttachInfoExtSaveReqVO {
    @Schema(description = "业务模块")
    private String businessModel;

    @Schema(description = "文件类型:1-图片；2-音频；3-视频", example = "2")
    private String fileType;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件路径")
    private String filePath;

}