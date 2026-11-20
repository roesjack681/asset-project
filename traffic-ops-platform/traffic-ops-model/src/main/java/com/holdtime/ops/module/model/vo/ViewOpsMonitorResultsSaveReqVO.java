package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - VIEW新增/修改 Request VO")
@Data
public class ViewOpsMonitorResultsSaveReqVO {

    @Schema(description = "设备ID", example = "10502")
    private String equId;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "所在道路名称", example = "芋艿")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
    private String intersectionName;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @Schema(description = "正常次数")
    private BigDecimal normalCnt;

    @Schema(description = "异常次数")
    private BigDecimal abnormalCnt;

    @Schema(description = "总检测天数")
    private BigDecimal monitorSumDays;

    @Schema(description = "总检测次数")
    private BigDecimal monitorSumCnt;

    @Schema(description = "健康率")
    private Double healthRate;

}