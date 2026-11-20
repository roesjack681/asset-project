package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 维保紧急程度配置新增/修改 Request VO")
@Data
public class TrafficOpsUrgencySettingsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9267")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "紧急程度")
    private String urgencyLevel;

    @Schema(description = "处理时限小时")
    private String deadline;

    @Schema(description = "通知人员")
    private String notifyPersonnel;

    @Schema(description = "通知人员openid")
    private String notifyOpenid;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}