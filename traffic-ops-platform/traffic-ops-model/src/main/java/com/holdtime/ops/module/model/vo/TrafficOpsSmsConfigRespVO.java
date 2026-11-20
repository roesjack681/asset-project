package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产短信发送配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsSmsConfigRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29409")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "发送类型：1重点考核视频；2重点考核卡口", example = "1")
    @ExcelProperty("发送类型：1重点考核视频；2重点考核卡口")
    private String sendType;

    @Schema(description = "接收人姓名", example = "王五")
    @ExcelProperty("接收人姓名")
    private String receiveName;

    @Schema(description = "接收人手机号")
    @ExcelProperty("接收人手机号")
    private String receivePhone;

    @Schema(description = "用户编号")
    @ExcelProperty("用户编号")
    private String userBh;

    @Schema(description = "接收状态:on;off", example = "2")
    @ExcelProperty("接收状态:on;off")
    private String receiveStatus;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}