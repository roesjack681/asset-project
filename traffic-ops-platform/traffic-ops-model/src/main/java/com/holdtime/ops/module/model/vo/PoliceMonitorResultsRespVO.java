package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.bytearray.ByteArrayImageConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.excel.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公安设备监测结果 Response VO")
@Data
@ExcelIgnoreUnannotated
@ContentRowHeight(120)
public class PoliceMonitorResultsRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1366")
//    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备类型", example = "2")
//    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "设备id", example = "22166")
//    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "设备名称", example = "李四")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "行政区域")
    @ExcelProperty("行政区域")
    private String unit;

    @Schema(description = "电警标签")
    @ExcelProperty("电警标签")
    private String equLabel;

    @Schema(description = "杆件编号")
//    @ExcelProperty("杆件编号")
    private String memberBh;

    @Schema(description = "方向")
//    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "检测事件", example = "1")
//    @ExcelProperty("检测事件")
    private String eventType;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    @ColumnWidth(72)
    private LocalDateTime occurrenceTime;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "检测结果图片")
    private String resultImg;

    @ExcelProperty(value = "检测结果图片", converter = ByteArrayImageConverter.class)
    @ColumnWidth(90)
    private byte[] imageBytes;

    @Schema(description = "是否为工单")
    @ExcelProperty("是否有工单")
    private String isRepairOrder;

    @Schema(description = "维保主键", example = "15594")
//    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "维护人员编号", example = "19675")
//    @ExcelProperty("维护人员编号")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    @ExcelProperty("维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
//    @ExcelProperty("维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "来源：1-接口；2-导入")
//    @ExcelProperty("来源：1-接口；2-导入")
    private String source;

    @Schema(description = "创建时间")
//    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
//    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "设备情况")
    @ExcelProperty("设备情况")
    private String equSituation;

    @Schema(description = "设备情况描述")
    @ExcelProperty("情况描述")
    private String equDesc;

    @Schema(description = "设备情况证据")
    private String evidenceFileId;

    @ExcelProperty(value = "现场照片", converter = ByteArrayImageConverter.class)
    @ColumnWidth(90)
    private byte[] evidenceBytes;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "备注时间")
    @ExcelProperty("备注时间")
    @ColumnWidth(72)
    private LocalDateTime remarkDate;

    @Schema(description = "修改人")
    private String updater;

    @Schema(description = "是否绑定灯杆")
    @ExcelProperty("是否绑定灯杆")
    private String isBindMember;

}