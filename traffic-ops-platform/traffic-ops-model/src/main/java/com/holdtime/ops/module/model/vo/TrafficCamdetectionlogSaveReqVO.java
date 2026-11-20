package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备状态检测新增/修改 Request VO")
@Data
public class TrafficCamdetectionlogSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29962")
    private Long id;

    @Schema(description = "路口ID", example = "1015")
    private Long lkid;

    @Schema(description = "相机ID", example = "30922")
    private Long sbid;

    @Schema(description = "相机状态，是否能打开视频流")
    private String xjzt;

    @Schema(description = "检测时间")
    private LocalDateTime jcsj;

}