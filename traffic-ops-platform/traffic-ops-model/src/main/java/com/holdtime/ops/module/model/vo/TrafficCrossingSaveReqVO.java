package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 路口信息新增/修改 Request VO")
@Data
public class TrafficCrossingSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6673")
    private Long id;

    @Schema(description = "路口名称")
    private String lkmc;

    @Schema(description = "路口类型")
    private String lklx;

    @Schema(description = "路口坐标，需要在地图显示时使用")
    private String lkzb;

    @Schema(description = "区旗县")
    private String qu;

}