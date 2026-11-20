package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "管理后台 - 设备维保信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceNearbyRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13818")
    private String id;

    @Schema(description = "工单号")
    private String orderNum;

    @Schema(description = "上报人员")
    private String reportPersonnel;

    @Schema(description = "设备地址")
    private String equAddr;

    @Schema(description = "设备类型", example = "2")
    @DictFormat("equ_type")
    private String equType;

    @Schema(description = "设备编号", example = "443")
    private String equId;

    @Schema(description = "报修时间")
    private Date faultDate;

    @Schema(description = "存在问题")
    private String problem;

    @Schema(description = "故障分类")
    private String faultType;

    @Schema(description = "紧急程度")
    private String urgencyLevel;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "设备经度")
    private String lng;

    @Schema(description = "设备纬度")
    private String lat;

    @Schema(description = "电警类型")
    private String djType;

    @Schema(description = "数据标签")
    private String equLable;

    @Schema(description = "安装位置")
    private String adress;

    @Schema(description = "距离（千米）")
    private String  distanceKm;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;








}