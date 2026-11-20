package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 道路信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsRoadRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21019")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "管理部门")
    @ExcelProperty("管理部门")
    private String glbm;

    @Schema(description = "道路代码")
    @ExcelProperty("道路代码")
    private String dldm;

    @Schema(description = "道路名称")
    @ExcelProperty("道路名称")
    private String dlmc;

    @Schema(description = "道路类型")
    @ExcelProperty("道路类型")
    private String dllx;

    @Schema(description = "公路行政等级")
    @ExcelProperty("公路行政等级")
    private String glxzdj;

    @Schema(description = "起始")
    @ExcelProperty("起始")
    private String qs;

    @Schema(description = "结束")
    @ExcelProperty("结束")
    private String js;

    @Schema(description = "辖区里程")
    @ExcelProperty("辖区里程")
    private String xqlc;

    @Schema(description = "起始名称")
    @ExcelProperty("起始名称")
    private String qsmc;

    @Schema(description = "结束名称")
    @ExcelProperty("结束名称")
    private String jsmc;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}