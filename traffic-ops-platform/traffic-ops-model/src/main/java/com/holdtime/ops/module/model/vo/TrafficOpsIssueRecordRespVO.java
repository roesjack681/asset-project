package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 领用记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsIssueRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29694")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "领用部门编号")
    @ExcelProperty("领用部门编号")
    private String useDeptBh;

    @Schema(description = "领用部门")
    @ExcelProperty("领用部门")
    private String useDept;

    @Schema(description = "领用人", example = "王五")
    @ExcelProperty("领用人")
    private String userName;

    @Schema(description = "领用人编号")
    @ExcelProperty("领用人编号")
    private String userBh;

    @Schema(description = "领用时间")
    @ExcelProperty("领用时间")
    private LocalDateTime issueTime;

    @Schema(description = "领用数量")
    @ExcelProperty("领用数量")
    private Integer issueNum;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "领用详情")
    private List<TrafficOpsIssueDetailRespVO> issueDetailList;

    @Schema(description = "剩余领用数量")
    @ExcelProperty("剩余领用数量")
    private Long detailIssueNum;
}