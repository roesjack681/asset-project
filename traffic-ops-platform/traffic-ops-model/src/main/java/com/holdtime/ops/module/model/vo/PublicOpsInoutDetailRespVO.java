package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备出入库存明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicOpsInoutDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "2565")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备出入库id", example = "3901")
    @ExcelProperty("设备出入库id")
    private String inoutId;

    @Schema(description = "耗材种类")
    @ExcelProperty("耗材种类")
    private String cmSort;

    @Schema(description = "耗材数量")
    @ExcelProperty("耗材数量")
    private Integer cmNum;

    @Schema(description = "新旧标识")
    @ExcelProperty("新旧标识")
    private String flag;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}