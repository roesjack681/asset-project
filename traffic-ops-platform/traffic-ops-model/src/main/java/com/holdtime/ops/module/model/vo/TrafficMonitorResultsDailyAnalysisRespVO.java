package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备监测结果每日分析 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficMonitorResultsDailyAnalysisRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23100")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "分析时间")
    @ExcelProperty("分析时间")
    private LocalDateTime analysisDate;

    @Schema(description = "设备ID", example = "23882")
    @ExcelProperty("设备ID")
    private String equId;

    @Schema(description = "杆件编号")
    @ExcelProperty("杆件编号")
    private String memberBh;

    @Schema(description = "道路名称", example = "赵六")
    @ExcelProperty("道路名称")
    private String roadName;

    @Schema(description = "交叉口名称", example = "芋艿")
    @ExcelProperty("交叉口名称")
    private String intersectionName;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "电警类型", example = "1")
    @ExcelProperty("电警类型")
    private String djType;

    @Schema(description = "设备标签")
    @ExcelProperty("设备标签")
    private String equLable;

    @Schema(description = "检测事件", example = "2")
    @ExcelProperty("检测事件")
    private String eventType;

    @Schema(description = "正常数")
    @ExcelProperty("正常数")
    private Integer normalCnt;

    @Schema(description = "异常数")
    @ExcelProperty("异常数")
    private Integer abnormalCnt;

    @Schema(description = "检测天数")
    @ExcelProperty("检测天数")
    private Integer monitorSumDays;

    @Schema(description = "检测总次数")
    @ExcelProperty("检测总次数")
    private Integer monitorSumCnt;

    @Schema(description = "健康率")
    @ExcelProperty("健康率")
    private BigDecimal healthRate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}