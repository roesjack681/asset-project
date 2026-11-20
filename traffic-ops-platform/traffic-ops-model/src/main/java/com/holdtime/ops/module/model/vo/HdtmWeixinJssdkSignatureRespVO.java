package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 微信JS SDK签名信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HdtmWeixinJssdkSignatureRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23739")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "签名")
    @ExcelProperty("签名")
    private String signature;

    @Schema(description = "时间戳")
    @ExcelProperty("时间戳")
    private String timessamp;

    @Schema(description = "随机字符串")
    @ExcelProperty("随机字符串")
    private String nonceStr;

    @Schema(description = "请求url", example = "https://www.iocoder.cn")
    @ExcelProperty("请求url")
    private String url;

    @Schema(description = "JS接口调用临时票据")
    @ExcelProperty("JS接口调用临时票据")
    private String jsapiTicket;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}