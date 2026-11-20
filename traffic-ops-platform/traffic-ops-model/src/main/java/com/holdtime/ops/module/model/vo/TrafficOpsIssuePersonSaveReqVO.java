package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备领用人信息新增/修改 Request VO")
@Data
public class TrafficOpsIssuePersonSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23966")
    private String id;

    @Schema(description = "用户名姓名", example = "李四")
    private String userName;

    @Schema(description = "部门编号", example = "11114")
    private String deptId;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}