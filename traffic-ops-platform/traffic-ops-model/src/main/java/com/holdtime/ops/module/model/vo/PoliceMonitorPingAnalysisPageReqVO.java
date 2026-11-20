package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 电警通断检测管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PoliceMonitorPingAnalysisPageReqVO extends PageParam {

    @Schema(description = "设备id", example = "4715")
    private String equId;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "自动ping正常数")
    private Integer autoPingNormalCnt;

    @Schema(description = "自动ping异常数")
    private Integer autoPingAbnormalCnt;

    @Schema(description = "自动ping健康率")
    private BigDecimal autoPingHealthRate;

    @Schema(description = "自动检测次数")
    private Integer autoMonitorCnt;

    @Schema(description = "ping正常数")
    private Integer pingNormalCnt;

    @Schema(description = "ping异常数")
    private Integer pingAbnormalCnt;

    @Schema(description = "ping健康率")
    private BigDecimal pingHealthRate;

    @Schema(description = "检测次数")
    private Integer monitorCnt;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}