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
public class ExcelImportVioEquipmentDailySituationDTO {
    @ExcelProperty("主键")
    private String id;

    @ExcelProperty("检测时间")
    private String occurrenceDay;

    @ExcelProperty("卡口编号")
    private String kkbh;

    @ExcelProperty("设备编号")
    private String sbbh;

    @ExcelProperty("无违法次数")
    private String noSurveilCnt;

    @ExcelProperty("有效违法数")
    private String validSurveilCnt;

    @ExcelProperty("无效违法数")
    private String invalidSurveilCnt;

    @ExcelProperty("识别准确率")
    private String recognitionAccuracyRate;

    @ExcelProperty("识别准确率采集时间")
    private String recognitionAccuracyPickDate;

    @ExcelProperty("创建时间")
    private String createTime;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}