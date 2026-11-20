package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 接口授权 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ApiAuthRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "150")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "客户端序列号")
    @ExcelProperty("客户端序列号")
    private String clientSn;

    @Schema(description = "授权码")
    @ExcelProperty("授权码")
    private String authCode;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    private LocalDateTime startDate;

    @Schema(description = "到期时间")
    @ExcelProperty("到期时间")
    private LocalDateTime expireDate;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}