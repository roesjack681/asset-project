package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 领用详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsIssueDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19943")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "领用编号")
    @ExcelProperty("领用编号")
    private String issueBh;

    @Schema(description = "装备编号", example = "24669")
    @ExcelProperty("装备编号")
    private String equId;

    @Schema(description = "使用部门编号")
    @ExcelProperty("使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    @ExcelProperty("使用部门")
    private String useDept;

    @Schema(description = "使用人", example = "王五")
    @ExcelProperty("使用人")
    private String userName;

    @Schema(description = "使用人编号")
    @ExcelProperty("使用人编号")
    private String userBh;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}