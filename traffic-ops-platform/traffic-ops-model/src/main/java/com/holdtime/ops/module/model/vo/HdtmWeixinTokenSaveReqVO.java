package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 微信Token新增/修改 Request VO")
@Data
public class HdtmWeixinTokenSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29461")
    private String id;

    @Schema(description = "微信接口调用票据")
    private String accessToken;

    @Schema(description = "获得票据时间")
    private String getTokenMillis;

    @Schema(description = "微信JS接口调用临时票据")
    private String jsapiTicket;

    @Schema(description = "获得临时票据时间")
    private String getTicketMillis;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}