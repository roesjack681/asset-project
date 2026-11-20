package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 电警 Response VO")
@Data
@ExcelIgnoreUnannotated
public class    TrafficOpsElectricPoliceRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27930")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "张三")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "软件版本")
    @ExcelProperty("软件版本")
    private String softwareVersion;

    @Schema(description = "所在道路名称", example = "芋艿")
    @ExcelProperty("所在道路名称")
    private String roadName;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "灯控路口名称", example = "芋艿")
    @ExcelProperty("灯控路口名称")
    private String intersectionName;

    @Schema(description = "电警类型", example = "1")
    @ExcelProperty(value = "电警类型", converter = DictConvert.class)
    @DictFormat("dj_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String djType;

    @Schema(description = "GPS定位-经度")
    @ExcelProperty("GPS定位-经度")
    private String lng;

    @Schema(description = "路网编号")
    @ExcelProperty("路网编号")
    private String lwBh;

    @Schema(description = "GPS定位-纬度")
    @ExcelProperty("GPS定位-纬度")
    private String lat;

    @Schema(description = "集指编号")
    @ExcelProperty("集指编号")
    private String jzBh;

    @Schema(description = "安装位置")
    @ExcelProperty("安装位置")
    private String adress;

    @Schema(description = "安装时间")
    @ExcelProperty("安装时间")
    private LocalDateTime installDate;

    @Schema(description = "厂家")
    @ExcelProperty("厂家")
    private String manufacturer;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String equModel;

    @Schema(description = "归属单位")
    @ExcelProperty("归属单位")
    private String unit;

    @Schema(description = "拍摄范围")
    @ExcelProperty("拍摄范围")
    private String shootingRange;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "镜头")
    @ExcelProperty("镜头")
    private String lens;

    @Schema(description = "焦距")
    @ExcelProperty("焦距")
    private String focalLength;

    @Schema(description = "光圈")
    @ExcelProperty("光圈")
    private String aperture;

    @Schema(description = "安装方式")
    @ExcelProperty("安装方式")
    private String installationMethod;

    @Schema(description = "通信方式")
    @ExcelProperty("通信方式")
    private String communicationModel;

    @Schema(description = "协议")
    @ExcelProperty("协议")
    private String protocol;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "网关")
    @ExcelProperty("网关")
    private String gateway;

    @Schema(description = "光交箱位置信息")
    @ExcelProperty("光交箱位置信息")
    private String gjxAddr;

    @Schema(description = "光交箱GPS定位-经度")
    @ExcelProperty("光交箱GPS定位-经度")
    private String gjxLng;

    @Schema(description = "光交箱GPS定位-纬度")
    @ExcelProperty("光交箱GPS定位-纬度")
    private String gjxLat;

    @Schema(description = "所属管辖单位")
    @ExcelProperty("所属管辖单位")
    private String dominion;

    @Schema(description = "状态")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "是否核查")
    private String isCheck;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "安装位置序号")
    private String addressXh;

    @Schema(description = "取电位置")
    private String powerSupplyLocation;

    @Schema(description = "维修次数")
    private Long maintenanceCnt;

    @Schema(description = "二维码信息", example = "1")
    private TrafficOpsQrcodeRespVO qrcodeRespVO;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "取证设备编号")
    private String sbbh;

    @Schema(description = "维保到期时间")
    @ExcelProperty("维保到期时间")
    private LocalDateTime maintenanceExpiryDate;

    @Schema(description = "是否在保")
    private String underWarranty;

    @Schema(description = "像素")
    private String pixel;

    @Schema(description = "拍摄方向")
    private String shotDirection;

    @Schema(description = "相机类型")
    private String xjType;

}