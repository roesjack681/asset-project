package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 领用详情新增/修改 Request VO")
@Data
public class TrafficOpsIssueDetailSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19943")
    private String id;

    @Schema(description = "领用编号")
    private String issueBh;

    @Schema(description = "装备编号", example = "24669")
    private String equId;

    @Schema(description = "使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    private String useDept;

    @Schema(description = "使用人", example = "王五")
    private String userName;

    @Schema(description = "使用人编号")
    private String userBh;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}