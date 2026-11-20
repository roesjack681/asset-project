package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 违法抓拍设备监控分析新增/修改 Request VO")
@Data
public class ViewVioEquipmentMonitorAnalysisSaveReqVO {

    @Schema(description = "设备id", example = "14147")
    private String equId;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "设备IP")
    private String ipAddr;

    @Schema(description = "")
    private BigDecimal pingNormalCnt;

    @Schema(description = "")
    private BigDecimal pingAbnormalCnt;

    @Schema(description = "")
    private BigDecimal pingHealthRate;

    @Schema(description = "")
    private BigDecimal videoNormalCnt;

    @Schema(description = "")
    private BigDecimal videoAbnormalCnt;

    @Schema(description = "")
    private BigDecimal videoHealthRate;

    @Schema(description = "")
    private BigDecimal noSurveilCnt;

    @Schema(description = "")
    private BigDecimal validSurveilCnt;

    @Schema(description = "")
    private BigDecimal invalidSurveilCnt;

    @Schema(description = "识别准确率")
    private BigDecimal recognitionAccuracyRate;

    @Schema(description = "识别准确率采集时间")
    private LocalDateTime recognitionAccuracyPickDate;

}