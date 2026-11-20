package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - VIEW新增/修改 Request VO")
@Data
public class ViewOpsMonitorSituationSaveReqVO {

    @Schema(description = "设备ID", example = "13324")
    private String equId;

    @Schema(description = "状态", example = "1")
    private String latestStatus;

    @Schema(description = "状态", example = "2")
    private String prevStatus;

    @Schema(description = "状态变化描述")
    private String statusChangeDesc;

}