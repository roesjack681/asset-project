package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 集指设备检测分析分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficVioEquipmentMonitorAnalysisExtPageReqVO extends TrafficVioEquipmentMonitorAnalysisPageReqVO {


    @Schema(description = "标签列表")
    private List<String> equLableList;

    @Schema(description = "电警类型", example = "2")
    private String djType;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "近几次通断状态")
    private String recentNPingStatus;

    @Schema(description = "通断状态")
    private String pingStatus;

    @Schema(description = "近几次视频状态")
    private String recentNVideoStatus;

    @Schema(description = "视频状态")
    private String videoStatus;

    @Schema(description = "近几次有无有效违法")
    private String recentNHasValidSurveilCnt;

    @Schema(description = "有无有效违法")
    private String hasValidSurveilCnt;

    @Schema(description = "有无识别准确率")
    private String hasRecognitionAccuracyRate;

    @Schema(description = "有效违法率查询运算")
    private String validSurveilOperator;

    @Schema(description = "有效违法率")
    private BigDecimal validSurveilRate;

    @Schema(description = "有效违法数查询运算")
    private String validSurveilCntOperator;

    @Schema(description = "无效违法数查询运算")
    private String invalidSurveilCntOperator;

    @Schema(description = "待审核违法数查询运算")
    private String noSurveilCntOperator;

    /**
     * 所属区域
     */
    private List<String> unitList;
}