package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 接口授权新增/修改 Request VO")
@Data
public class ApiAuthSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "150")
    private String id;

    @Schema(description = "客户端序列号")
    private String clientSn;

    @Schema(description = "授权码")
    private String authCode;

    @Schema(description = "开始时间")
    private LocalDateTime startDate;

    @Schema(description = "到期时间")
    private LocalDateTime expireDate;

    @Schema(description = "备注", example = "随便")
    private String remark;

}