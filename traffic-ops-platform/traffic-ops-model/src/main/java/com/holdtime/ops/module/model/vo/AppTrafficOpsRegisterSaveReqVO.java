package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "用户 APP - 人员注册绑定信息新增/修改 Request VO")
@Data
public class AppTrafficOpsRegisterSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6718")
    private String id;

    @Schema(description = "姓名", example = "李四")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "验证码")
    private String validCode;

    @Schema(description = "openID", example = "28058")
    private String openid;

    @Schema(description = "用户id", example = "4129")
    private String userId;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}