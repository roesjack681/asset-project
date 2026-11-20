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
public class ExcelImportPoliceDjMonitorDTO {

    @ExcelProperty("IP地址")
    private String ipAddr;

    @ExcelProperty("IP通达情况")
    private String status;

    @ExcelProperty("检测时间")
    private String occurrenceTime;

    @ExcelProperty("照片1")
    private String imgHex1;

    @ExcelProperty("照片2")
    private String imgHex2;

    @ExcelProperty("照片3")
    private String imgHex3;

    @ExcelProperty("照片4")
    private String imgHex4;

    @ExcelProperty("照片5")
    private String imgHex5;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}