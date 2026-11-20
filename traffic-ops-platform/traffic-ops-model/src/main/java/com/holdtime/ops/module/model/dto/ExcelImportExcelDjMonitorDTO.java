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
public class ExcelImportExcelDjMonitorDTO {

    @ExcelProperty("设备IP")
    private String ipAddr;

    @ExcelProperty("检测类型")
    private String eventType;

    @ExcelProperty("检测结果")
    private String monitorResult;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}