package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 区域用户组信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicUserAreaGroupRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11840")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安")
    @ExcelProperty("所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "组名", example = "芋艿")
    @ExcelProperty("组名")
    private String groupName;

    @Schema(description = "主要运维内容")
    @ExcelProperty("主要运维内容")
    private String groupContent;

    @Schema(description = "可维护区域")
    @ExcelProperty("可维护区域")
    private String groupArea;

    @Schema(description = "组员id", example = "18919")
    @ExcelProperty("组员id")
    private String groupUserId;

    @Schema(description = "队长id", example = "11673")
    @ExcelProperty("队长id")
    private Long leadUserId;

    @Schema(description = "队长姓名")
    private String leadUserName;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}