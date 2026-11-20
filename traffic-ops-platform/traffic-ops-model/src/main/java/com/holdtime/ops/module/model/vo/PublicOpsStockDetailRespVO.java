package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备库存明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicOpsStockDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12570")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "库存编号", example = "14491")
    @ExcelProperty("库存编号")
    private String stockId;

    @Schema(description = "耗材种类")
    @ExcelProperty("耗材种类")
    private String cmSort;

    @Schema(description = "耗材数量")
    @ExcelProperty("耗材数量")
    private Integer cmNum;

    @Schema(description = "新旧标识")
    @ExcelProperty("新旧标识")
    private String flag;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}