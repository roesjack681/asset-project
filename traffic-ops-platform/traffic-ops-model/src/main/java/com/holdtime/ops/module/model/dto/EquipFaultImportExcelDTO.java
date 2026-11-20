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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class EquipFaultImportExcelDTO {

    @ExcelProperty("所在道路名称")
    private String intersectionName;

    @ExcelProperty("方向")
    private String direction;

    @ExcelProperty("设备类型")
    @NotBlank
    private String equipType;

    @ExcelProperty("故障内容")
    @NotBlank
    private String problem;

    @ExcelProperty("设备ID")
    @NotBlank
    private String equipId;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}