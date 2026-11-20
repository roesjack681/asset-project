package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 集指设备检测分析分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficVioEquipmentMonitorAnalysisPageReqVO extends PageParam {

    @Schema(description = "设备id", example = "18900")
    private String equId;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "视频正常数")
    private Integer videoNormalCnt;

    @Schema(description = "视频异常数")
    private Integer videoAbnormalCnt;

    @Schema(description = "视频健康率")
    private BigDecimal videoHealthRate;

    @Schema(description = "ping正常数")
    private Integer pingNormalCnt;

    @Schema(description = "ping异常数")
    private Integer pingAbnormalCnt;

    @Schema(description = "ping健康率")
    private BigDecimal pingHealthRate;

    @Schema(description = "无违法次数")
    private Integer noSurveilCnt;

    @Schema(description = "有效违法数")
    private Integer validSurveilCnt;

    @Schema(description = "无效违法数")
    private Integer invalidSurveilCnt;

    @Schema(description = "识别准确率")
    private BigDecimal recognitionAccuracyRate;

    @Schema(description = "检测次数")
    private Integer monitorCnt;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}