package com.holdtime.ops.module.model.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
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
public class ExcelImportPoliceEquipDTO {

    @ExcelProperty("序号")
    private String serialNum;

    @ExcelProperty("设备类型")
    private String equType;

    @ExcelProperty("S/N")
    private String psn;

    @ExcelProperty("个呼号")
    private String callSign;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("型号")
    private String model;

    @ExcelProperty("生产厂家")
    private String manufacturer;

    @ExcelProperty("供货厂家")
    private String supplier;

    @ExcelProperty("电池")
    private String battery;

    @ExcelProperty("领用人")
    private String monitorResult;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}