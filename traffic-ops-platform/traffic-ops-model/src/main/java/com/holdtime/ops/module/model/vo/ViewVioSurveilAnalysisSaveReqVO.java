package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - VIEW新增/修改 Request VO")
@Data
public class ViewVioSurveilAnalysisSaveReqVO {

    @Schema(description = "主键", example = "4461")
    private String equId;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "电警类型", example = "2")
    private String djType;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测日期")
    private LocalDate occurrenceDay;

    @Schema(description = "正常数量")
    private BigDecimal normalCnt;

    @Schema(description = "异常数量")
    private BigDecimal abnormalCnt;

    @Schema(description = "检测总天数")
    private Long monitorSumDays;

    @Schema(description = "检测总次数")
    private Long monitorSumCnt;

    @Schema(description = "健康率")
    private BigDecimal healthRate;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "检测图片")
    private String resultImg;

    @Schema(description = "检测情况")
    private String monitorSituation;

    @Schema(description = "无违法数量")
    @NotNull(message = "不能为空")
    private Long noSurveilCnt;

    @Schema(description = "有效违法数量")
    @NotNull(message = "不能为空")
    private Long validSurveilCnt;

    @Schema(description = "无效违法数量")
    @NotNull(message = "不能为空")
    private Long invalidSurveilCnt;

    @Schema(description = "识别准确率")
    private BigDecimal recognitionAccuracyRate;

    @Schema(description = "识别准确率采集时间")
    private LocalDateTime recognitionAccuracyPickDate;

}