package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备监测结果历史 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficMonitorResultsHistoryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30873")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "检测事件", example = "2")
    @ExcelProperty("检测事件")
    private String eventType;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "来源：1-接口；2-导入")
    @ExcelProperty("来源：1-接口；2-导入")
    private String source;

    @Schema(description = "设备ID", example = "12040")
    @ExcelProperty("设备ID")
    private String equId;

    @Schema(description = "所在道路名称", example = "王五")
    @ExcelProperty("所在道路名称")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "王五")
    @ExcelProperty("灯控路口名称")
    private String intersectionName;

    @Schema(description = "杆件编号")
    @ExcelProperty("杆件编号")
    private String memberBh;

}