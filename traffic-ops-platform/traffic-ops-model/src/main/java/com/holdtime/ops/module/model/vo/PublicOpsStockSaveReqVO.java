package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备库存管理新增/修改 Request VO")
@Data
public class PublicOpsStockSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11035")
    private String id;

    @Schema(description = "耗材种类")
    private String cmSort;

    @Schema(description = "耗材当前数量")
    private Integer surplusNum;

    @Schema(description = "总入库量")
    private Integer totalNum;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}