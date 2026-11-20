package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备库存管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicOpsStockRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11035")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "耗材种类")
    @ExcelProperty("耗材种类")
    private String cmSort;

    @Schema(description = "耗材当前数量")
    @ExcelProperty("耗材当前数量")
    private Integer surplusNum;

    @Schema(description = "总入库量")
    @ExcelProperty("总入库量")
    private Integer totalNum;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}