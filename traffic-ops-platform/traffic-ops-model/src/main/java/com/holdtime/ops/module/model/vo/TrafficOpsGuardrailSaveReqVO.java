package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护栏新增/修改 Request VO")
@Data
public class TrafficOpsGuardrailSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21097")
    private String id;

    @Schema(description = "设备名称", example = "李四")
    private String equName;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "所在道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "赵六")
    private String intersectionName;

    @Schema(description = "起始GPS定位-经度")
    private String startLng;

    @Schema(description = "起始GPS定位-纬度")
    private String startLat;

    @Schema(description = "截止GPS定位-经度")
    private String endLng;

    @Schema(description = "截止GPS定位-纬度")
    private String endLat;

    @Schema(description = "安装位置")
    private String adress;

    @Schema(description = "安装时间")
    private LocalDateTime installDate;

    @Schema(description = "归属单位")
    private String unit;

    @Schema(description = "所属管辖单位")
    private String dominion;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

    @Schema(description = "总片数")
    private Integer pieces;

    @Schema(description = "总长数")
    private BigDecimal length;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}