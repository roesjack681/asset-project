package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 标线分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsMarklinePageReqVO extends PageParam {

    @Schema(description = "道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "路段")
    private String roadParts;

    @Schema(description = "施画种类")
    private String marklineSort;

    @Schema(description = "数量")
    private String marklineNum;

    @Schema(description = "线宽")
    private String lineWidth;

    @Schema(description = "规格")
    private String specifications;

    @Schema(description = "比例")
    private String proportion;

    @Schema(description = "面积")
    private String area;

    @Schema(description = "施画单位")
    private String paintingUnit;

    @Schema(description = "施划工艺")
    private String drawingProcess;

    @Schema(description = "施划时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applicationTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

    @Schema(description = "所属管辖单位")
    private String dominion;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

}