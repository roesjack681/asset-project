package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 终端设备信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicOpsDeviceRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24305")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "model")
    @ExcelProperty("model")
    private String model;

    @Schema(description = "IMEI")
    @ExcelProperty("IMEI")
    private String imei;

    @Schema(description = "车牌号")
    @ExcelProperty("车牌号")
    private String plateNum;

    @Schema(description = "摄像头信息")
    @ExcelProperty("摄像头信息")
    private String cameraInfo;

    @Schema(description = "状态", example = "1")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}