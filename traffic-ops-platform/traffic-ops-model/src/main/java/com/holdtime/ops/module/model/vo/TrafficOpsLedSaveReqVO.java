package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - Led屏新增/修改 Request VO")
@Data
public class TrafficOpsLedSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20928")
    private String id;

    @Schema(description = "设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "王五")
    private String equName;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "所在道路名称", example = "王五")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "王五")
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

    @Schema(description = "尺寸")
    private String size;

    @Schema(description = "显示用途")
    private String displayPurpose;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}