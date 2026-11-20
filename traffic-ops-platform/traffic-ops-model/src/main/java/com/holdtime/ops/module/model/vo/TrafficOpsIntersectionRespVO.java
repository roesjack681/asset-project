package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 交叉口信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsIntersectionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28295")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "管理部门")
    @ExcelProperty("管理部门")
    private String glbm;

    @Schema(description = "道路代码")
    @ExcelProperty("道路代码")
    private String dldm;

    @Schema(description = "行政区划")
    @ExcelProperty("行政区划")
    private String xzqh;

    @Schema(description = "路段代码")
    @ExcelProperty("路段代码")
    private String lddm;

    @Schema(description = "道路名称")
    @ExcelProperty("道路名称")
    private String dlmc;

    @Schema(description = "路段名称")
    @ExcelProperty("路段名称")
    private String ldmc;

    @Schema(description = "起始里程")
    @ExcelProperty("起始里程")
    private String qslc;

    @Schema(description = "结束里程")
    @ExcelProperty("结束里程")
    private String jslc;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}