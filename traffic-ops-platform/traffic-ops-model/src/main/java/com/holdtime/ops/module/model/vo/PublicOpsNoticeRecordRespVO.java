package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.desensitize.core.slider.annotation.MobileDesensitize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 消息通知记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicOpsNoticeRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27868")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "业务类型", example = "2")
    @ExcelProperty("业务类型")
    private String businessType;

    @Schema(description = "业务主键", example = "5883")
    @ExcelProperty("业务主键")
    private String businessId;

    @Schema(description = "消息类型:1-SMS;2-IM;3-mail", example = "1")
    @ExcelProperty("消息类型:1-SMS;2-IM;3-mail")
    private String noticeType;

    @Schema(description = "接收者类型", example = "1")
    @ExcelProperty("接收者类型")
    private String receiveUserType;

    @Schema(description = "接收者ID", example = "25133")
    @ExcelProperty("接收者ID")
    private String receiveUserId;

    @Schema(description = "接收者姓名", example = "王五")
    @ExcelProperty("接收者姓名")
    private String receiveUserName;

    @Schema(description = "接收者电话")
    @ExcelProperty("接收者电话")
    @MobileDesensitize
    private String receiveUserPhone;

    @Schema(description = "通知消息")
    @ExcelProperty("通知消息")
    private String noticeMsg;

    @Schema(description = "发送时间")
    @ExcelProperty("发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}