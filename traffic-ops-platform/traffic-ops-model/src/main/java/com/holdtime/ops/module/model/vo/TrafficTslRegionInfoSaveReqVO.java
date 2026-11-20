package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控区域信息新增/修改 Request VO")
@Data
public class TrafficTslRegionInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12918")
    private String id;

    @Schema(description = "区域名称", example = "赵六")
    private String regionName;

    @Schema(description = "tcsID", example = "2034")
    private String tcsId;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}