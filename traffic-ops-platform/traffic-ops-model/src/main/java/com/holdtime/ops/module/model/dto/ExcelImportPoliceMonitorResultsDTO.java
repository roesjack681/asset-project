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
public class ExcelImportPoliceMonitorResultsDTO {

    @ExcelProperty("设备IP")
    private String ipAddr;

    @ExcelProperty("诊断时间")
    private String occurrenceTime;

    @ExcelProperty("实时视频")
    private String videoStatus;

    @ExcelProperty("ping")
    private String pingStatus;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}