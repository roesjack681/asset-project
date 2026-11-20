package com.holdtime.ops.module.model.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ElectricFaultImportExcelDTO {

    @ExcelProperty("上报人员")
    @NotBlank(message = "上报人员不能为空")
    private String reportPersonnel;

    @ExcelProperty("报修地点")
    @NotBlank(message = "报修地点不能为空")
    private String equAddr;

    @ExcelProperty("报修时间")
    @NotBlank(message = "报修时间不能为空")
    private String reportTime;

    @ExcelProperty("上报内容")
    @NotBlank(message = "上报内容不能为空")
    private String problem;

    @ExcelProperty("设备IP")
    @NotBlank(message = "设备IP不能为空")
    private String equipIP;

    @ExcelProperty("故障分类")
    @NotBlank(message = "故障分类不能为空")
    private String faultType;

    @ExcelProperty("维护人员")
//    @NotBlank(message = "维护人员不能为空")
    private String maintenancePersonnel;

    @ExcelProperty("设备ID")
    private String equipId;

    @ExcelProperty("失败原因")
    private String failReason;
}