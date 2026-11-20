package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 杆件附属设备 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsAccessoryEquipmentRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29797")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "杆件编号", example = "23096")
    @ExcelProperty("杆件编号")
    private String memberId;

    @Schema(description = "设备类型名称", example = "1")
    @ExcelProperty("设备类型名称")
    private String equType;

    @Schema(description = "厂家")
    @ExcelProperty("厂家")
    private String manufacturer;

    @Schema(description = "设备型号")
    @ExcelProperty("设备型号")
    private String equModel;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private String equNum;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}