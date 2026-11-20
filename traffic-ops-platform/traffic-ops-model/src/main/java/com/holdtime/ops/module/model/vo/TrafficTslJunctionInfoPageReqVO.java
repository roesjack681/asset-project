package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信控交叉口信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficTslJunctionInfoPageReqVO extends PageParam {

    @Schema(description = "所属区域ID", example = "30719")
    private String regionId;

    @Schema(description = "交叉口名称", example = "李四")
    private String junctionName;

    @Schema(description = "控制ID", example = "2313")
    private String controllerId;

    @Schema(description = "控制名称", example = "芋艿")
    private String controllerName;

    @Schema(description = "协议类型")
    private String tcpudp;

    @Schema(description = "协议编号")
    private String protocol;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}