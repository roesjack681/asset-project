package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 违法抓拍设备监控分析 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViewVioEquipmentMonitorAnalysisRespVO {

    @Schema(description = "设备id", example = "14147")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "卡口编号")
    @ExcelProperty("卡口编号")
    private String kkbh;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String sbbh;

    @Schema(description = "设备IP")
    @ExcelProperty("设备IP")
    private String ipAddr;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal pingNormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal pingAbnormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal pingHealthRate;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal videoNormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal videoAbnormalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal videoHealthRate;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal noSurveilCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal validSurveilCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal invalidSurveilCnt;

    @Schema(description = "有效违法率")
    @ExcelProperty("有效违法率")
    private BigDecimal validSurveilRate;

    @Schema(description = "识别准确率")
    @ExcelProperty("识别准确率")
    private BigDecimal recognitionAccuracyRate;

    @Schema(description = "识别准确率采集时间")
    @ExcelProperty("识别准确率采集时间")
    private LocalDateTime recognitionAccuracyPickDate;

}