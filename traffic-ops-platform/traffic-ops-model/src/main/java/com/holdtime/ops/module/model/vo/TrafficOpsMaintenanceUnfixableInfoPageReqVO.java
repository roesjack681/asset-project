package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备维保无法修复信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsMaintenanceUnfixableInfoPageReqVO extends PageParam {

    @Schema(description = "无法修复类型：1-施工；2-设备；3-线路", example = "1")
    private String unfixableType;

    @Schema(description = "无法修复原因", example = "不喜欢")
    private String unfixableReason;

    @Schema(description = "施工方联系人", example = "2")
    private String constructionContacts;

    @Schema(description = "联系电话", example = "222")
    private String phone;

    @Schema(description = "预计可维修时间")
    private LocalDateTime estimatedRepairDate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}