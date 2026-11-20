package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公安监测结果导入分析分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PoliceMonitorResultsImportAnalysisPageReqVO extends PageParam {

    @Schema(description = "设备id", example = "8624")
    private String equId;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

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

    @Schema(description = "检测次数")
    private Integer monitorCnt;

    @Schema(description = "上次修复时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] lastRepairTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}