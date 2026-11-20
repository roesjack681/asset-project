package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 违法抓拍设备监控分析分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewVioEquipmentMonitorAnalysisPageReqVO extends PageParam {

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
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] recognitionAccuracyPickDate;

}