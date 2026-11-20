package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 维保记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6439")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "工单号")
    @ExcelProperty("工单号")
    private String orderNum;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "设备编号", example = "31610")
    @ExcelProperty("设备编号")
    private String equId;

    @Schema(description = "设备地址")
    @ExcelProperty("设备地址")
    private String equAddr;

    @Schema(description = "设备经度")
    @ExcelProperty("设备经度")
    private String equLng;

    @Schema(description = "设备纬度")
    @ExcelProperty("设备纬度")
    private String equLat;

    @Schema(description = "存在问题")
    @ExcelProperty("存在问题")
    private String problem;

    @Schema(description = "故障分类", example = "2")
    @ExcelProperty("故障分类")
    private String faultType;

    @Schema(description = "故障时间")
    @ExcelProperty("故障时间")
    private LocalDateTime faultDate;

    @Schema(description = "上报人员")
    @ExcelProperty("上报人员")
    private String reportPersonnel;

    @Schema(description = "上报人员编号", example = "12625")
    @ExcelProperty("上报人员编号")
    private String reportUserId;

    @Schema(description = "上报时间")
    @ExcelProperty("上报时间")
    private LocalDateTime reportTime;

    @Schema(description = "上报人员部门编号", example = "30173")
    @ExcelProperty("上报人员部门编号")
    private String reportDeptId;

    @Schema(description = "上报人员部门")
    @ExcelProperty("上报人员部门")
    private String reportDept;

    @Schema(description = "派单人员编号", example = "20889")
    @ExcelProperty("派单人员编号")
    private String dispatchUserId;

    @Schema(description = "派单人")
    @ExcelProperty("派单人")
    private String dispatch;

    @Schema(description = "派单时间")
    @ExcelProperty("派单时间")
    private LocalDateTime dispatchTime;

    @Schema(description = "派单地址")
    @ExcelProperty("派单地址")
    private String dispatchAdress;

    @Schema(description = "派单经度")
    @ExcelProperty("派单经度")
    private String dispatchLng;

    @Schema(description = "派单纬度")
    @ExcelProperty("派单纬度")
    private String dispatchLat;

    @Schema(description = "接单地址")
    @ExcelProperty("接单地址")
    private String receiveAdress;

    @Schema(description = "接单经度")
    @ExcelProperty("接单经度")
    private String receiveLng;

    @Schema(description = "接单纬度")
    @ExcelProperty("接单纬度")
    private String receiveLat;

    @Schema(description = "接单时间")
    @ExcelProperty("接单时间")
    private LocalDateTime receiveDate;

    @Schema(description = "维护人员编号", example = "25902")
    @ExcelProperty("维护人员编号")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    @ExcelProperty("维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    @ExcelProperty("维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "维护建议")
    @ExcelProperty("维护建议")
    private String suggestion;

    @Schema(description = "取消人员")
    @ExcelProperty("取消人员")
    private String cancelPersonnel;

    @Schema(description = "取消人员编号", example = "27239")
    @ExcelProperty("取消人员编号")
    private String cancelUserId;

    @Schema(description = "取消时间")
    @ExcelProperty("取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因", example = "不喜欢")
    @ExcelProperty("取消原因")
    private String cancelReason;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "紧急程度")
    @ExcelProperty("紧急程度")
    private String urgencyLevel;

    @Schema(description = "最后处理时间")
    @ExcelProperty("最后处理时间")
    private LocalDateTime deadline;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "数据来源")
    private String source;

    @Schema(description = "取电位置")
    private String powerSupplyLocation;

    @Schema(description = "完成时间")
    private LocalDateTime finishTime;

    @Schema(description = "耗时")
    private Double workTime;

    @Schema(description = "维护时间")
    @ExcelProperty("维护时间")
    private LocalDateTime maintenanceDate;

    @Schema(description = "维护情况")
    @ExcelProperty("维护情况")
    private String maintenance;

    @Schema(description = "维护成员")
    private String maintenanceTeamMembers;

    @Schema(description = "确认人")
    private String confirmor;

    @Schema(description = "确认人员编号")
    private String confirmorUserId;

    @Schema(description = "确认时间")
    private LocalDateTime confirmDate;

    @Schema(description = "驳回人员编号")
    private String rejectUserId;

    @Schema(description = "驳回人员")
    private String rejectPersonnel;

    @Schema(description = "驳回时间")
    private LocalDateTime rejectTime;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "预计修复难度")
    private String projectedRepair;

    @Schema(description = "实际修复难度")
    private String actualRepair;

    @Schema(description = "处置方式")
    private String disposalMethod;

}