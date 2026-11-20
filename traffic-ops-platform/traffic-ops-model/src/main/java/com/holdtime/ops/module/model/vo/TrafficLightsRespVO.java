package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信号灯信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficLightsRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18328")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "路口ID", example = "1477")
    @ExcelProperty("路口ID")
    private Long lkid;

    @Schema(description = "信号灯方向")
    @ExcelProperty("信号灯方向")
    private String xhdfx;

    @Schema(description = "相机方向")
    @ExcelProperty("相机方向")
    private String xjfx;

    @Schema(description = "信号灯状态")
    @ExcelProperty("信号灯状态")
    private String xhdzt;

    @Schema(description = "上次检测时间")
    @ExcelProperty("上次检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "相机地址")
    @ExcelProperty("相机地址")
    private String xjdz;

    @Schema(description = "处置结果")
    @ExcelProperty("处置结果")
    private String czjg;

    @Schema(description = "是否检测")
    @ExcelProperty("是否检测")
    private String sfjc;

    @Schema(description = "坐标")
    @ExcelProperty("坐标")
    private String zb;

    @Schema(description = "信号灯型号")
    @ExcelProperty("信号灯型号")
    private String xhdxh;

    @Schema(description = "检测目标：例：GB1:3(此方向有3个GB1灯);FGB1:2;FGBF1:2(2个非国标机动车灯，2个非国标非机动车灯)")
    @ExcelProperty("检测目标：例：GB1:3(此方向有3个GB1灯);FGB1:2;FGBF1:2(2个非国标机动车灯，2个非国标非机动车灯)")
    private String jcmb;

    @Schema(description = "常黑状态个数")
    @ExcelProperty("常黑状态个数")
    private Integer ch;

    @Schema(description = "信号灯识别区域")
    @ExcelProperty("信号灯识别区域")
    private String xhdsbqy;

    @Schema(description = "拥堵识别区域")
    @ExcelProperty("拥堵识别区域")
    private String ydsbqy;

}