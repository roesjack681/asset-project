package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 日常巡检信息新增/修改 Request VO")
@Data
public class PoliceDailyRoundCheckSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9617")
    private String id;

    @Schema(description = "巡检设备编号", example = "3125")
    private String equId;

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "设备状态", example = "1")
    private String equStatus;

    @Schema(description = "巡检项")
    private String checkItem;

    @Schema(description = "问题")
    private String question;

    @Schema(description = "解决方案")
    private String solution;

    @Schema(description = "巡检人")
    private String checkPerson;

    @Schema(description = "巡检方式：内勤；外勤", example = "2")
    private String checkType;

    @Schema(description = "巡检人编号", example = "28789")
    private String checkPersonId;

    @Schema(description = "巡检时间")
    private LocalDateTime checkDate;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}