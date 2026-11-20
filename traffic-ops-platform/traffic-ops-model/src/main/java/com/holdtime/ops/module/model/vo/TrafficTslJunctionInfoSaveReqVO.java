package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口信息新增/修改 Request VO")
@Data
public class TrafficTslJunctionInfoSaveReqVO {

    @Schema(description = "交叉口ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
    private String id;

    @Schema(description = "所属区域ID", example = "30719")
    private String regionId;

    @Schema(description = "交叉口名称", example = "李四")
    private String junctionName;

    @Schema(description = "控制ID", example = "2313")
    private String controllerId;

    @Schema(description = "控制名称", example = "芋艿")
    private String controllerName;

    @Schema(description = "协议类型")
    private String tcpudp;

    @Schema(description = "协议编号")
    private String protocol;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}