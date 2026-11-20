package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备领用人信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsIssuePersonRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23966")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "用户名姓名", example = "李四")
    @ExcelProperty("用户名姓名")
    private String userName;

    @Schema(description = "部门编号", example = "11114")
    @ExcelProperty("部门编号")
    private String deptId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}