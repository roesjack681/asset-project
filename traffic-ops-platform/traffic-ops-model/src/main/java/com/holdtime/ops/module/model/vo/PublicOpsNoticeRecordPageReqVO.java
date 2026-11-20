package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 消息通知记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PublicOpsNoticeRecordPageReqVO extends PageParam {

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
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}