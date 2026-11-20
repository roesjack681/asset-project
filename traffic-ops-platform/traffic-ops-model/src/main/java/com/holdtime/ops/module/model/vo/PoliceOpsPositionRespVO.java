package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 交叉口信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceOpsPositionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28295")
    private String id;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "设备名称")
    private String equName;

    @Schema(description = "设备地址")
    private String address;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "管辖单位")
    private String dominion;

    @Schema(description = "设备状态")
    private String status;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "电警-电警类型")
    private String djType;

    @Schema(description = "电警--方向")
    private String direction;


    @Schema(description = "杆件——设备图片")
    private String equImg;

    @Schema(description = "杆件——序列号")
    private Integer serialNum;

    @Schema(description = "杆件——安装位置")
    private String adress;
}