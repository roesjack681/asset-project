package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备出入库管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicOpsInoutStockRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4243")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "出入库类型", example = "2")
    @ExcelProperty("出入库类型")
    private String stockType;

    @Schema(description = "操作人")
    @ExcelProperty("操作人")
    private String stockPerson;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}