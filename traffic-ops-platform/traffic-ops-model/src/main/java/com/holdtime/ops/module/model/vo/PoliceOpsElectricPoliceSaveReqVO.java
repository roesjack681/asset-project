package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 电警新增/修改 Request VO")
@Data
public class PoliceOpsElectricPoliceSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27933")
    private String id;

    @Schema(description = "设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "张三")
    private String equName;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "所在道路名称", example = "王五")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "芋艿")
    private String intersectionName;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "安装位置")
    private String adress;

    @Schema(description = "安装时间")
    private LocalDateTime installDate;

    @Schema(description = "厂家")
    private String manufacturer;

    @Schema(description = "型号")
    private String equModel;

    @Schema(description = "归属单位")
    private String unit;

    @Schema(description = "拍摄范围")
    private String shootingRange;

    @Schema(description = "镜头")
    private String lens;

    @Schema(description = "焦距")
    private String focalLength;

    @Schema(description = "光圈")
    private String aperture;

    @Schema(description = "软件版本")
    private String softwareVersion;

    @Schema(description = "安装方式")
    private String installationMethod;

    @Schema(description = "通信方式")
    private String communicationModel;

    @Schema(description = "协议")
    private String protocol;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "网关")
    private String gateway;

    @Schema(description = "光交箱位置信息")
    private String gjxAddr;

    @Schema(description = "光交箱GPS定位-经度")
    private String gjxLng;

    @Schema(description = "光交箱GPS定位-纬度")
    private String gjxLat;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "电警类型", example = "1")
    private String djType;

    @Schema(description = "路网编号")
    private String lwBh;

    @Schema(description = "集指编号")
    private String jzBh;

    @Schema(description = "管辖单位")
    private String dominion;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "是否核查")
    private String isCheck;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "用户名", example = "赵六")
    private String userName;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "安装位置序号")
    private String addressXh;

    @Schema(description = "维保到期时间")
    private LocalDateTime maintenanceExpiryDate;

    @Schema(description = "设备编码")
    private String equCode;

    @Schema(description = "是否在保")
    private String underWarranty;

    @Schema(description = "像素")
    private String pixel;
}