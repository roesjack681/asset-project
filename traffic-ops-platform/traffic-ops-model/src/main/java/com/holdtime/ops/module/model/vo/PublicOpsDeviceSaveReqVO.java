package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 终端设备信息新增/修改 Request VO")
@Data
public class PublicOpsDeviceSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24305")
    private String id;

    @Schema(description = "model")
    private String model;

    @Schema(description = "IMEI")
    private String imei;

    @Schema(description = "车牌号")
    private String plateNum;

    @Schema(description = "摄像头信息")
    private String cameraInfo;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}