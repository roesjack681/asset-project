package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 检测结果每日分析视图 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViewOpsMonitorResultsDailyAnalysisRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23518")
    @ExcelProperty("主键")
    private String equId;

    @Schema(description = "杆件编号")
    @ExcelProperty("杆件编号")
    private String memberBh;

    @Schema(description = "所在道路名称", example = "赵六")
    @ExcelProperty("所在道路名称")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
    @ExcelProperty("灯控路口名称")
    private String intersectionName;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "电警类型", example = "2")
    @ExcelProperty("电警类型")
    private String djType;

    @Schema(description = "设备标签")
    @ExcelProperty("设备标签")
    private String equLable;

    @Schema(description = "检测事件", example = "1")
    @ExcelProperty("检测事件")
    private String eventType;

    @Schema(description = "")
    @ExcelProperty("")
    private String occurrenceDay;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal normalCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal abnormalCnt;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("")
    private Long monitorSumDays;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("")
    private Long monitorSumCnt;

    @Schema(description = "")
    @ExcelProperty("")
    private BigDecimal healthRate;

    @Schema(description = "杆件编号")
    @ExcelProperty("杆件编号")
    private String memberName;

}