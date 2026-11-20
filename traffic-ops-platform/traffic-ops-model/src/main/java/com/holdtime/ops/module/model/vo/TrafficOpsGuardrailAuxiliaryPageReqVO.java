package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 护栏辅助设备分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsGuardrailAuxiliaryPageReqVO extends PageParam {

    @Schema(description = "护栏主键", example = "1822")
    private String guardrailId;

    @Schema(description = "维保主键", example = "30047")
    private String maintenanceId;

    @Schema(description = "护栏维护主键", example = "7771")
    private String manceGuardrailId;

    @Schema(description = "辅助设备名称", example = "王五")
    private String auxiliaryName;

    @Schema(description = "数量")
    private Integer auxiliaryNum;

    @Schema(description = "图片")
    private String auxiliaryImg;

    @Schema(description = "状态：安装；拆除", example = "2")
    private String status;

    @Schema(description = "安装时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] installTime;

    @Schema(description = "拆除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] demolishTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}