package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 区域用户组信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PublicUserAreaGroupPageReqVO extends PageParam {

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "组名", example = "芋艿")
    private String groupName;

    @Schema(description = "主要运维内容")
    private String groupContent;

    @Schema(description = "可维护区域")
    private String groupArea;

    @Schema(description = "组员id", example = "18919")
    private String groupUserId;

    @Schema(description = "队长id", example = "11673")
    private Long leadUserId;

    @Schema(description = "队长姓名")
    private String leadUserName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}