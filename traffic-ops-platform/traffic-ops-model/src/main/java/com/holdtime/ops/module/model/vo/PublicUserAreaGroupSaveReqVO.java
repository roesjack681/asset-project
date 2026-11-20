package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 区域用户组信息新增/修改 Request VO")
@Data
public class PublicUserAreaGroupSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11840")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "组名", example = "芋艿")
    private String groupName;

    @Schema(description = "主要运维内容")
    private String groupContent;

    @Schema(description = "可维护区域")
    private String groupArea;

    @Schema(description = "组员id", example = "18919")
    private String groupUserId;

    @Schema(description = "队长id", example = "11673")
    private Long leadUserId;

    @Schema(description = "队长姓名")
    private String leadUserName;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}