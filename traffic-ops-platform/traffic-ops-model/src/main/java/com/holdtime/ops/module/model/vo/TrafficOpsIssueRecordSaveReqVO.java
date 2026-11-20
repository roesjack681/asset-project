package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 领用记录新增/修改 Request VO")
@Data
public class TrafficOpsIssueRecordSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29694")
    private String id;

    @Schema(description = "领用部门编号")
    private String useDeptBh;

    @Schema(description = "领用部门")
    private String useDept;

    @Schema(description = "领用人", example = "王五")
    private String userName;

    @Schema(description = "领用人编号")
    private String userBh;

    @Schema(description = "领用时间")
    private LocalDateTime issueTime;

    @Schema(description = "领用数量")
    private Integer issueNum;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}