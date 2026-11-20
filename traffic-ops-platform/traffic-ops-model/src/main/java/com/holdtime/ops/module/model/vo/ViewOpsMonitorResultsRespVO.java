package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - VIEW Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViewOpsMonitorResultsRespVO {

    @Schema(description = "设备ID", example = "10502")
//    @ExcelProperty("设备ID")
    private String equId;

    @Schema(description = "杆件编号")
//    @ExcelProperty("杆件编号")
    private String memberBh;

    @Schema(description = "所在道路名称", example = "芋艿")
//    @ExcelProperty("所在道路名称")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
//    @ExcelProperty("灯控路口名称")
    private String intersectionName;

    @Schema(description = "方向")
//    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "IP地址")
    @ExcelProperty(value = "IP地址", index = 0)
    private String ipAddr;

    @Schema(description = "检测事件", example = "1")
//    @ExcelProperty("检测事件")
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
    @ExcelProperty(value = "健康率", index = 2)
    private Double healthRate;

}