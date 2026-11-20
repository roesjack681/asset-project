package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 护栏开口信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsGuardrailGapPageReqVO extends PageParam {

    @Schema(description = "护栏主键", example = "10933")
    private String guardrailId;

    @Schema(description = "维保主键", example = "15132")
    private String maintenanceId;

    @Schema(description = "护栏维护主键", example = "5415")
    private String manceGuardrailId;

    @Schema(description = "开口名称", example = "芋艿")
    private String gapName;

    @Schema(description = "开口类型", example = "2")
    private String gapType;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}