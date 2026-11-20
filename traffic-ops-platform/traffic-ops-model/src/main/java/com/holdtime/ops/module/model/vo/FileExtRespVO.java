package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 上传文件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FileExtRespVO {
    @Schema(description = "文件编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "原文件名", requiredMode = Schema.RequiredMode.REQUIRED, example = "yudao.jpg" )
    private String name;
    @Schema(description = "文件 URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/yudao.jpg")
    private String url;
    @Schema( description = "文件MIME类型", example = "application/octet-stream")
    private String type;

}
