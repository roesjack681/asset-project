package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 电动自行车导入新增/修改 Request VO")
@Data
public class ImportRecordSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1961")
    private String id;

    @Schema(description = "导入类型")
    @ExcelProperty("导入类型")
    private String importType;

    @Schema(description = "导入文件名")
    @ExcelProperty("导入文件名")
    private String fileName;

    @Schema(description = "导入文件")
    private String importFile;

    @Schema(description = "导入记录数")
    private Integer importNum;

    @Schema(description = "成功记录数")
    private Integer sucNum;

    @Schema(description = "失败记录数")
    private Integer failNum;

    @Schema(description = "失败文件")
    private String failFile;

}