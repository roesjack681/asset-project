package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 护栏辅助设备维护详细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsMaintenanceAuxiliaryPageReqVO extends PageParam {

    @Schema(description = "维保主键", example = "1463")
    private String maintenanceId;

    @Schema(description = "护栏主键", example = "21345")
    private String guardrailId;

    @Schema(description = "辅助设备ID", example = "23263")
    private String auxiliaryId;

    @Schema(description = "辅助设备名称", example = "赵六")
    private String auxiliaryName;

    @Schema(description = "数量")
    private Integer auxiliaryNum;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}