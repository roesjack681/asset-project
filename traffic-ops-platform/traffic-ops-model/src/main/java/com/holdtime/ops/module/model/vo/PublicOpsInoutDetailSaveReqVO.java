package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备出入库存明细新增/修改 Request VO")
@Data
public class PublicOpsInoutDetailSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "2565")
    private String id;

    @Schema(description = "设备出入库id", example = "3901")
    private String inoutId;

    @Schema(description = "耗材种类")
    private String cmSort;

    @Schema(description = "耗材数量")
    private Integer cmNum;

    @Schema(description = "新旧标识")
    private String flag;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}