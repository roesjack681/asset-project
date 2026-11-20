package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口设备信息新增/修改 Request VO")
@Data
public class TrafficTslJunctionDeviceInfoSaveReqVO {

    @Schema(description = "信号机", requiredMode = Schema.RequiredMode.REQUIRED, example = "22195")
    private String id;

    @Schema(description = "信号机ID")
    private String signalId;

    @Schema(description = "信号机名称", example = "王五")
    private String controllerName;

    @Schema(description = "交叉口ID", example = "2759")
    private String junctionId;

    @Schema(description = "交叉口名称", example = "王五")
    private String junctionName;

    @Schema(description = "设备IP")
    private String deviceIp;

    @Schema(description = "在线状态")
    private String online;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "模式")
    private String mode;

    @Schema(description = "协议")
    private String protocol;

    @Schema(description = "能否ping")
    private String canPing;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}