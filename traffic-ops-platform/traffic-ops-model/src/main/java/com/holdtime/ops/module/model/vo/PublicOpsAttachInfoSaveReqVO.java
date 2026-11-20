package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产附件信息新增/修改 Request VO")
@Data
public class PublicOpsAttachInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20821")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "业务模块")
    private String businessModel;

    @Schema(description = "业务id", example = "13654")
    private String businessId;

    @Schema(description = "文件类型:1-图片；2-音频；3-视频", example = "2")
    private String fileType;

    @Schema(description = "文件名称", example = "李四")
    private String fileName;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "文件大小")
    private Integer fileSize;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}