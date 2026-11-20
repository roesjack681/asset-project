package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 标线 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMarklineRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11228")
//    @ExcelProperty("主键")
    private String id;

    @Schema(description = "道路名称", example = "赵六")
    @ExcelProperty("道路名称")
    @ColumnWidth(52)
    private String roadName;

    @Schema(description = "路段")
    @ExcelProperty("路段")
    @ColumnWidth(42)
    private String roadParts;

    @Schema(description = "施画种类")
    @ExcelProperty("施画种类")
    @ColumnWidth(15)
    private String marklineSort;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private String marklineNum;

    @Schema(description = "线宽")
    @ExcelProperty("线宽")
    private String lineWidth;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String specifications;

    @Schema(description = "比例")
    @ExcelProperty("比例")
    private String proportion;

    @Schema(description = "面积")
    @ExcelProperty("面积")
    private String area;

    @Schema(description = "施画单位")
    @ExcelProperty("施画单位")
    @ColumnWidth(15)
    private String paintingUnit;

    @Schema(description = "施划工艺")
//    @ExcelProperty("施划工艺")
    private String drawingProcess;

    @Schema(description = "施划时间")
//    @ExcelProperty("施划时间")
    private LocalDateTime applicationTime;

    @Schema(description = "创建时间")
//    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
//    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "所属管辖单位")
    @ExcelProperty("管辖单位")
    @ColumnWidth(15)
    private String dominion;

    @Schema(description = "状态")
//    @ExcelProperty("状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

}