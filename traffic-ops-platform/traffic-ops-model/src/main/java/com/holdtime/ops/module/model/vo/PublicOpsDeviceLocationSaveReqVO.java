package com.holdtime.ops.module.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 终端最新位置新增/修改 Request VO")
@Data
public class PublicOpsDeviceLocationSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12870")
    private String id;

    @Schema(description = "终端编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2921")
    @NotEmpty(message = "终端编号不能为空")
    private String deviceId;

    @Schema(description = "GPS纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "GPS纬度不能为空")
    private BigDecimal latitude;

    @Schema(description = "GPS经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "GPS经度不能为空")
    private BigDecimal longitude;

    @Schema(description = "GPS速度(km/h)")
    private BigDecimal speed;

    @Schema(description = "方向(0-359度)")
    private Integer direction;

    @Schema(description = "位置上报时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "位置上报时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportTime;

}