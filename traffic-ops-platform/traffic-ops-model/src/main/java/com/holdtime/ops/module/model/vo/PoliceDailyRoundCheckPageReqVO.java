package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 日常巡检信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PoliceDailyRoundCheckPageReqVO extends PageParam {

    @Schema(description = "巡检设备编号", example = "3125")
    private String equId;

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "设备状态", example = "1")
    private String equStatus;

    @Schema(description = "巡检项")
    private String checkItem;

    @Schema(description = "问题")
    private String question;

    @Schema(description = "解决方案")
    private String solution;

    @Schema(description = "巡检人")
    private String checkPerson;

    @Schema(description = "巡检方式：内勤；外勤", example = "2")
    private String checkType;

    @Schema(description = "巡检人编号", example = "28789")
    private String checkPersonId;

    @Schema(description = "巡检时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] checkDate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}