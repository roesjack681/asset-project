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

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ExcelImportVioInvaliReasonDTO {
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String sbbh;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private String occurrenceTime;

    @Schema(description = "无效原因", example = "不对")
    @ExcelProperty("无效原因")
    private String invalidReason;

    @Schema(description = "无效数量")
    @ExcelProperty("无效数量")
    private Integer invalidCnt;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private String createTime;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}