package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "用户 APP - 维保材料信息新增/修改 Request VO")
@Data
public class AppTrafficOpsMaintenanceFilesSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4038")
    private String id;

    @Schema(description = "维保主键", example = "29809")
    private String maintenanceId;

    @Schema(description = "维保进度id", example = "8674")
    private String progressId;

    @Schema(description = "报修类型：上报；维修", example = "1")
    private String repairType;

    @Schema(description = "材料类型：图片；音频；视频", example = "1")
    private String fileType;

    @Schema(description = "材料url", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}