package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 日常巡检信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceDailyRoundCheckRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9617")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "巡检设备编号", example = "3125")
    @ExcelProperty("巡检设备编号")
    private String equId;

    @Schema(description = "所属单位：1-交警；2-公安")
    @ExcelProperty("所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "设备状态", example = "1")
    @ExcelProperty("设备状态")
    private String equStatus;

    @Schema(description = "巡检项")
    @ExcelProperty("巡检项")
    private String checkItem;

    @Schema(description = "问题")
    @ExcelProperty("问题")
    private String question;

    @Schema(description = "解决方案")
    @ExcelProperty("解决方案")
    private String solution;

    @Schema(description = "巡检人")
    @ExcelProperty("巡检人")
    private String checkPerson;

    @Schema(description = "巡检方式：内勤；外勤", example = "2")
    @ExcelProperty("巡检方式：内勤；外勤")
    private String checkType;

    @Schema(description = "巡检人编号", example = "28789")
    @ExcelProperty("巡检人编号")
    private String checkPersonId;

    @Schema(description = "巡检时间")
    @ExcelProperty("巡检时间")
    private LocalDateTime checkDate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}