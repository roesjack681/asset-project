package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 公安监测结果导入分析分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PoliceMonitorResultsImportAnalysisExtPageReqVO extends PoliceMonitorResultsImportAnalysisPageReqVO {

    @Schema(description = "设备IP", example = "8624")
    @ExcelProperty("设备IP")
    private String ipAddr;

    @Schema(description = "设备名称")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "Ping状态")
    @ExcelProperty("Ping状态")
    private String pingStatus;

    @Schema(description = "实时视频状态")
    @ExcelProperty("实时视频状态")
    private String videoStatus;

    @Schema(description = "近两次Ping状态")
    @ExcelProperty("近两次Ping状态")
    private String finalPingStatus;

    @Schema(description = "近两次视频状态")
    @ExcelProperty("近两次视频状态")
    private String finalVideoStatus;

    @Schema(description = "查询开始时间")
    @ExcelProperty("查询开始时间")
    private String startDate;

    @Schema(description = "查询结束时间")
    @ExcelProperty("查询结束时间")
    private String endDate;

}