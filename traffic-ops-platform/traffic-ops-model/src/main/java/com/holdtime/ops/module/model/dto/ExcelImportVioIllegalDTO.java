package com.holdtime.ops.module.model.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ExcelImportVioIllegalDTO {
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8318")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String sbbh;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private String occurrenceTime;

    @Schema(description = "违法原因", example = "不好")
    @ExcelProperty("违法原因")
    private String illegalReason;

    @Schema(description = "违法数量")
    @ExcelProperty("违法数量")
    private Integer illegalCnt;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private String createTime;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}