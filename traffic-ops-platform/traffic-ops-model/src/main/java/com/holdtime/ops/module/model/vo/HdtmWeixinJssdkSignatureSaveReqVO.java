package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 微信JS SDK签名信息新增/修改 Request VO")
@Data
public class HdtmWeixinJssdkSignatureSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23739")
    private String id;

    @Schema(description = "签名")
    private String signature;

    @Schema(description = "时间戳")
    private String timessamp;

    @Schema(description = "随机字符串")
    private String nonceStr;

    @Schema(description = "请求url", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "JS接口调用临时票据")
    private String jsapiTicket;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}