package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备维保信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsMaintenancePageReqVO extends PageParam {

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "工单号")
    @ExcelProperty("工单号")
    private String orderNum;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "设备编号", example = "443")
    private String equId;

    @Schema(description = "设备地址")
    private String equAddr;

    @Schema(description = "设备经度")
    private String equLng;

    @Schema(description = "设备纬度")
    private String equLat;

    @Schema(description = "存在问题")
    private String problem;

    @Schema(description = "故障分类", example = "1")
    private String faultType;

    @Schema(description = "故障时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] faultDate;

    @Schema(description = "上报人员")
    private String reportPersonnel;

    @Schema(description = "上报人员编号", example = "24804")
    private String reportUserId;

    @Schema(description = "上报时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] reportTime;

    @Schema(description = "上报人员部门编号")
    private String reportDeptId;

    @Schema(description = "上报人员部门")
    private String reportDept;

    @Schema(description = "派单人员编号", example = "10601")
    private String dispatchUserId;

    @Schema(description = "派单人")
    private String dispatch;

    @Schema(description = "派单时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] dispatchTime;

    @Schema(description = "派单地址")
    private String dispatchAdress;

    @Schema(description = "派单经度")
    private String dispatchLng;

    @Schema(description = "派单纬度")
    private String dispatchLat;

    @Schema(description = "接单地址")
    private String receiveAdress;

    @Schema(description = "接单经度")
    private String receiveLng;

    @Schema(description = "接单纬度")
    private String receiveLat;

    @Schema(description = "维护人员编号")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "维护建议")
    private String suggestion;

    @Schema(description = "取消人员编号")
    private String cancelUserId;

    @Schema(description = "取消人员")
    private String cancelPersonnel;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因")
    private String cancelReason;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "紧急程度")
    private String urgencyLevel;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

    @Schema(description = "确认人")
    private String confirmor;

    @Schema(description = "确认人员编号")
    private String confirmorUserId;

    @Schema(description = "确认时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] confirmDate;

    @Schema(description = "数据来源")
    private String source;

    @Schema(description = "完成时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] finishTime;

    @Schema(description = "工作耗时")
    private Double workTime;

    @Schema(description = "驳回人员编号")
    private String rejectUserId;

    @Schema(description = "驳回人员")
    private String rejectPersonnel;

    @Schema(description = "驳回时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] rejectTime;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "标签列表")
    private List<String> equLableList;

    @Schema(description = "预计修复难度")
    private String projectedRepair;

    @Schema(description = "实际修复难度")
    private String actualRepair;

    @Schema(description = "处置方式")
    private String disposalMethod;

    @Schema(description = "维修次数操作符")
    private String repairCntOperator;

    @Schema(description = "维修次数")
    private Integer repairCnt;

}