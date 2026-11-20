package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 警用装备历史领用记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsIssueHistoryPageReqVO extends PageParam {

    @Schema(description = "领用部门编号")
    private String useDeptBh;

    @Schema(description = "领用部门")
    private String useDept;

    @Schema(description = "领用人", example = "芋艿")
    private String issueUserName;

    @Schema(description = "领用人编号")
    private String issueUserBh;

    @Schema(description = "领用时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] issueTime;

    @Schema(description = "装备编号", example = "9699")
    private String equId;

    @Schema(description = "PSN", example = "9699")
    private String psn;

    @Schema(description = "使用人", example = "芋艿")
    private String userName;

    @Schema(description = "使用人编号")
    private String userBh;

    @Schema(description = "回收人")
    private String recycleUserBh;

    @Schema(description = "回收人编号", example = "李四")
    private String recycleUserName;

    @Schema(description = "回收时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] recycleTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}