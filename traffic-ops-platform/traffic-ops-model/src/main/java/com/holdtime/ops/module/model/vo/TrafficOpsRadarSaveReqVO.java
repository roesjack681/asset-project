package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 雷达新增/修改 Request VO")
@Data
public class TrafficOpsRadarSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30805")
    private String id;

    @Schema(description = "设备名称", example = "赵六")
    private String equName;

    @Schema(description = "设备编号")
    private String equNumber;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "软件版本")
    private String softwareVersion;

    @Schema(description = "所在道路名称", example = "芋艿")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "赵六")
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

    @Schema(description = "工作频率")
    private String operatingFrequency;

    @Schema(description = "探测距离")
    private String detectionRange;

    @Schema(description = "探测角度")
    private String detectionAngle;

    @Schema(description = "安装方式")
    private String installationMethod;

    @Schema(description = "通信方式")
    private String communicationModel;

    @Schema(description = "协议")
    private String protocol;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "所属管辖单位")
    private String dominion;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

}