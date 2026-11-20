package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 人员注册绑定信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppTrafficOpsRegisterRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6718")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "姓名", example = "李四")
    @ExcelProperty("姓名")
    private String name;

    @Schema(description = "手机号")
    @ExcelProperty("手机号")
    private String phone;

    @Schema(description = "openID", example = "28058")
    @ExcelProperty("openID")
    private String openid;

    @Schema(description = "用户id", example = "4129")
    @ExcelProperty("用户id")
    private String userId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}