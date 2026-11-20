package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "用户 APP - 设备维保耗材新增/修改 Request VO")
@Data
public class AppTrafficOpsMaintainCmSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30666")
    private String id;

    @Schema(description = "维修进度主键", example = "844")
    private String progressId;

    @Schema(description = "耗材种类")
    private String cmSort;

    @Schema(description = "耗材数量")
    private String cmNum;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}