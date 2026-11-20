package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 警用装备历史领用记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsIssueHistoryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11717")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "领用部门编号")
    @ExcelProperty("领用部门编号")
    private String useDeptBh;

    @Schema(description = "领用部门")
    @ExcelProperty("领用部门")
    private String useDept;

    @Schema(description = "领用人", example = "芋艿")
    @ExcelProperty("领用人")
    private String issueUserName;

    @Schema(description = "领用人编号")
    @ExcelProperty("领用人编号")
    private String issueUserBh;

    @Schema(description = "领用时间")
    @ExcelProperty("领用时间")
    private LocalDateTime issueTime;

    @Schema(description = "装备编号", example = "9699")
    @ExcelProperty("装备编号")
    private String equId;

    @Schema(description = "PSN", example = "9699")
    @ExcelProperty("PSN")
    private String psn;

    @Schema(description = "使用人", example = "芋艿")
    @ExcelProperty("使用人")
    private String userName;

    @Schema(description = "使用人编号")
    @ExcelProperty("使用人编号")
    private String userBh;

    @Schema(description = "回收人")
    @ExcelProperty("回收人")
    private String recycleUserBh;

    @Schema(description = "回收人编号", example = "李四")
    @ExcelProperty("回收人编号")
    private String recycleUserName;

    @Schema(description = "回收时间")
    @ExcelProperty("回收时间")
    private LocalDateTime recycleTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}