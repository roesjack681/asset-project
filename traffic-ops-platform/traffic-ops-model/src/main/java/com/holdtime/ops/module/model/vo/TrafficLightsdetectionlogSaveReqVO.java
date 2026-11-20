package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信号灯检测信息新增/修改 Request VO")
@Data
public class TrafficLightsdetectionlogSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9085")
    private Long id;

    @Schema(description = "路口ID", example = "27850")
    private Long lkid;

    @Schema(description = "相机ID", example = "24353")
    private Long sbid;

    @Schema(description = "检测结果")
    private String jcjg;

    @Schema(description = "检测结果扩展信息")
    private String jcjgex;

    @Schema(description = "检测图片PATH")
    private String jctp;

    @Schema(description = "检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "判断结果")
    private String pdjg;

}