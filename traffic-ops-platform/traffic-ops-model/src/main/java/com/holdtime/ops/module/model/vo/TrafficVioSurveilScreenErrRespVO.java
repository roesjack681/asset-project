package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 违法抓拍无效记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficVioSurveilScreenErrRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24349")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备id", example = "31977")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String sbbh;

    @Schema(description = "号牌号码")
    @ExcelProperty("号牌号码")
    private String hphm;

    @Schema(description = "号牌种类")
    @ExcelProperty("号牌种类")
    private String hpzl;

    @Schema(description = "违法时间")
    @ExcelProperty("违法时间")
    private LocalDateTime wfsj;

    @Schema(description = "违法行为")
    @ExcelProperty("违法行为")
    private String wfxw;

    @Schema(description = "复审结果")
    private String reviewResult;

    @Schema(description = "复审描述")
    private String reviewDesc;

    @Schema(description = "复审时间")
    private LocalDateTime reviewDate;

    @Schema(description = "复审人")
    private String reviewer;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}