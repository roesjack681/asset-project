package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 标志牌新增/修改 Request VO")
@Data
public class TrafficOpsSignBoardSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12537")
    private String id;

    @Schema(description = "设备名称", example = "王五")
    private String equName;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "所在道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
    private String intersectionName;

    @Schema(description = "安装时间")
    private LocalDateTime installDate;

    @Schema(description = "安装位置")
    private String adress;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "标牌内容")
    private String labelContent;

    @Schema(description = "规格")
    private String specifications;

    @Schema(description = "规格单位")
    private String ggunit;

    @Schema(description = "数量")
    private String num;

    @Schema(description = "归属单位")
    private String unit;

    @Schema(description = "所属管辖单位")
    private String dominion;

    @Schema(description = "生产厂家")
    private String manufacturer;

    @Schema(description = "安装厂家")
    private String installManufacturer;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

}