package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - VIEW分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewOpsMonitorSituationPageReqVO extends PageParam {

    @Schema(description = "设备ID", example = "13324")
    private String equId;

    @Schema(description = "状态", example = "1")
    private String latestStatus;

    @Schema(description = "状态", example = "2")
    private String prevStatus;

    @Schema(description = "状态变化描述")
    private String statusChangeDesc;

}