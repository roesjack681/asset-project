package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备库存明细新增/修改 Request VO")
@Data
public class PublicOpsStockDetailSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12570")
    private String id;

    @Schema(description = "库存编号", example = "14491")
    private String stockId;

    @Schema(description = "耗材种类")
    private String cmSort;

    @Schema(description = "耗材数量")
    private Integer cmNum;

    @Schema(description = "新旧标识")
    private String flag;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}