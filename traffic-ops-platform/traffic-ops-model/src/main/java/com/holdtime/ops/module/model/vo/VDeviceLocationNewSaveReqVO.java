package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 车辆最新位置查询新增/修改 Request VO")
@Data
public class VDeviceLocationNewSaveReqVO {

    @Schema(description = "IMEI")
    private String imei;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "车牌号")
    private String plateNum;

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15950")
    @NotEmpty(message = "主键不能为空")
    private String id;

    @Schema(description = "终端编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "431")
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
    private LocalDateTime reportTime;

}