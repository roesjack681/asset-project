package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 微信Token Response VO")
@Data
@ExcelIgnoreUnannotated
public class HdtmWeixinTokenRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29461")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "微信接口调用票据")
    @ExcelProperty("微信接口调用票据")
    private String accessToken;

    @Schema(description = "获得票据时间")
    @ExcelProperty("获得票据时间")
    private String getTokenMillis;

    @Schema(description = "微信JS接口调用临时票据")
    @ExcelProperty("微信JS接口调用临时票据")
    private String jsapiTicket;

    @Schema(description = "获得临时票据时间")
    @ExcelProperty("获得临时票据时间")
    private String getTicketMillis;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}