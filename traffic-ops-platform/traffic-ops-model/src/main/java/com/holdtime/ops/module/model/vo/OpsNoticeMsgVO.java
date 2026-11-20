package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 电警 Response VO")
@Data
public class OpsNoticeMsgVO {
    @Schema(description = "业务主键")
    private String businessId;

    @Schema(description = "消息接收用户ID")
    private Long userId;

    @Schema(description = "消息接收手机号")
    private String receivePhone;

    @Schema(description = "消息类型")
    private String msgType;

    @Schema(description = "通知消息")
    private String noticeMsg;

}