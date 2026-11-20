package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产二维码生成记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsQrcodeGenRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16474")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "区域编码")
    private String geoCode;

    @Schema(description = "生成日期")
    @ExcelProperty("生成日期")
    private LocalDateTime genDate;

    @Schema(description = "道路ID", example = "12198")
    @ExcelProperty("道路ID")
    private String roadId;

    @Schema(description = "道路名称", example = "李四")
    @ExcelProperty("道路名称")
    private String roadName;

    @Schema(description = "资产类型", example = "1")
    @ExcelProperty("资产类型")
    private String opsType;

    @Schema(description = "方向和数量(json存储)")
    @ExcelProperty("方向和数量(json存储)")
    private String directionCnt;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}