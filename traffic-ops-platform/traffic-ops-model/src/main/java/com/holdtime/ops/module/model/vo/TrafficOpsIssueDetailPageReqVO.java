package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 领用详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsIssueDetailPageReqVO extends PageParam {

    @Schema(description = "领用编号")
    private String issueBh;

    @Schema(description = "装备编号", example = "24669")
    private String equId;

    @Schema(description = "使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    private String useDept;

    @Schema(description = "使用人", example = "王五")
    private String userName;

    @Schema(description = "使用人编号")
    private String userBh;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}