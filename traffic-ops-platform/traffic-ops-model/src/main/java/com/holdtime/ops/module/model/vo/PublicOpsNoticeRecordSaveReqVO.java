package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 消息通知记录新增/修改 Request VO")
@Data
public class PublicOpsNoticeRecordSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27868")
    private String id;

    @Schema(description = "业务类型", example = "2")
    private String businessType;

    @Schema(description = "业务主键", example = "5883")
    private String businessId;

    @Schema(description = "消息类型:1-SMS;2-IM;3-mail", example = "1")
    private String noticeType;

    @Schema(description = "接收者类型", example = "1")
    private String receiveUserType;

    @Schema(description = "接收者ID", example = "25133")
    private String receiveUserId;

    @Schema(description = "接收者姓名", example = "王五")
    private String receiveUserName;

    @Schema(description = "接收者电话")
    private String receiveUserPhone;

    @Schema(description = "通知消息")
    private String noticeMsg;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}