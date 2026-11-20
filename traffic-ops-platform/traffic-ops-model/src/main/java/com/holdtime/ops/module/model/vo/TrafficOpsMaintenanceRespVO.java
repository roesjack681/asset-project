package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.holdtime.ops.module.model.dto.TrafficOpsEquipInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 设备维保信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13818")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "工单号")
    private String orderNum;

    @Schema(description = "设备类型", example = "2")
    @DictFormat("equ_type")
    private String equType;

    @Schema(description = "设备编号", example = "443")
    private String equId;

    @Schema(description = "设备地址")
    @ExcelProperty("设备地址")
    @ColumnWidth(72)
    private String equAddr;

    @Schema(description = "设备经度")
    private String equLng;

    @Schema(description = "设备纬度")
    private String equLat;

    @Schema(description = "存在问题")
    @ExcelProperty(value = "存在问题", index = 1)
    @ColumnWidth(80)
    private String problem;

    @Schema(description = "故障分类")
    @ExcelProperty(value = "故障分类", index = 3)
    @ColumnWidth(25)
    private String faultType;

    @Schema(description = "报修时间")
    @ExcelProperty(value = "报修时间", index= 6)
    @ColumnWidth(25)
    private Date faultDate;

    @Schema(description = "上报人员")
    @ExcelProperty(value = "上报人员", index = 0)
    @ColumnWidth(18)
    private String reportPersonnel;

    @Schema(description = "上报人员编号", example = "24804")
//    @ExcelProperty("上报人员编号")
    private String reportUserId;

    @Schema(description = "上报人电话", example = "24804")
//    @ExcelProperty("上报人员编号")
    private String reportUserPhone;

    @Schema(description = "上报时间")
//    @ExcelProperty(value = "上报时间", index = 6)
    @ColumnWidth(25)
    private LocalDateTime reportTime;

    @Schema(description = "上报人员部门编号")
    private String reportDeptId;

    @Schema(description = "上报人员部门")
//    @ExcelProperty("上报人员部门")
    @ColumnWidth(25)
    private String reportDept;

    @Schema(description = "派单人员编号", example = "10601")
//    @ExcelProperty("派单人员编号")
    private String dispatchUserId;

    @Schema(description = "派单人")
    @ExcelProperty(value = "派单人", index = 4)
    @ColumnWidth(18)
    private String dispatch;

    @Schema(description = "派单时间")
    @ExcelProperty(value = "派单时间", index = 5)
    @ColumnWidth(25)
    private LocalDateTime dispatchTime;

    @Schema(description = "派单地址")
//    @ExcelProperty("派单地址")
//    @ColumnWidth(72)
    private String dispatchAdress;

    @Schema(description = "派单经度")
//    @ExcelProperty("派单经度")
    private String dispatchLng;

    @Schema(description = "派单纬度")
//    @ExcelProperty("派单纬度")
    private String dispatchLat;

    @Schema(description = "接单地址")
//    @ExcelProperty("接单地址")
//    @ColumnWidth(72)
    private String receiveAdress;

    @Schema(description = "接单经度")
//    @ExcelProperty("接单经度")
    private String receiveLng;

    @Schema(description = "接单纬度")
//    @ExcelProperty("接单纬度")
    private String receiveLat;

    @Schema(description = "接单时间")
    @ExcelProperty("接单时间")
    @ColumnWidth(25)
    private LocalDateTime receiveDate;

    @Schema(description = "维护人员编号")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    @ExcelProperty(value = "维护人员", index = 10)
    @ColumnWidth(84)
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    @ExcelProperty(value = "维护人员手机号", index = 11)
    @ColumnWidth(25)
    private String maintenancePhone;

    @Schema(description = "维护建议")
//    @ExcelProperty("维护建议")
//    @ColumnWidth(72)
    private String suggestion;

    @Schema(description = "维护时间")
    @ExcelProperty(value = "维护时间", index = 12)
    @ColumnWidth(25)
    private LocalDateTime maintenanceDate;

    @Schema(description = "维护内容")
    @ExcelProperty(value = "维护内容", index = 13)
    @ColumnWidth(120)
    private String maintenance;

    @Schema(description = "维护成员")
    @ExcelProperty(value = "维护成员", index = 14)
    @ColumnWidth(84)
    private String maintenanceTeamMembers;

    @Schema(description = "取消人员编号")
    private String cancelUserId;

    @Schema(description = "取消人员")
    private String cancelPersonnel;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因")
    private String cancelReason;

    @Schema(description = "状态", example = "2")
    @ColumnWidth(15)
    @DictFormat("maintenance_status")
    @ExcelProperty(value = "状态", converter = DictConvert.class, index = 9)
    private String status;

    @Schema(description = "备注", example = "你猜")
//    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "紧急程度")
    private String urgencyLevel;

    @Schema(description = "创建时间")
//    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
//    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    private List<TrafficOpsMaintenanceFileDTO> fileDTOList;

    @Schema(description = "确认人")
    private String confirmor;

    @Schema(description = "确认人员编号")
    private String confirmorUserId;

    @Schema(description = "确认时间")
    private LocalDateTime confirmDate;

    @Schema(description = "数据来源")
    private String source;

    @Schema(description = "取电位置")
    private String powerSupplyLocation;

    @Schema(description = "完成时间")
    @ExcelProperty(value = "完成时间", index = 7)
    @ColumnWidth(25)
    private LocalDateTime finishTime;

    @Schema(description = "工作耗时")
    @ExcelProperty(value = "用时[小时]", index = 8)
    private Double workTime;

    @Schema(description = "驳回人员编号")
    private String rejectUserId;

    @Schema(description = "驳回人员")
    private String rejectPersonnel;

    @Schema(description = "驳回时间")
    private LocalDateTime rejectTime;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "在线率")
    private String onlineRate;

    @Schema(description = "电警类型")
    private String djType;

    @Schema(description = "维修次数")
    private Integer repairCnt;

    @Schema(description = "报修设备信息")
    private TrafficOpsEquipInfoDTO equipInfoDTO;

    @Schema(description = "电警标签")
    @ExcelProperty(value = "电警标签", index = 2)
    private String equLabel;

    @Schema(description = "预计修复难度")
    private String projectedRepair;

    @Schema(description = "实际修复难度")
    private String actualRepair;

    @Schema(description = "处置方式")
    private String disposalMethod;

}