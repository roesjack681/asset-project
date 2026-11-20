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

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ExcelImportPoliceMonitorPingDetailDTO {
    @ExcelProperty("主键")
    private String id;

    @ExcelProperty("IP地址")
    private String ipAddr;

    @ExcelProperty("检测时间")
    private String occurrenceTime;

    @ExcelProperty("ping状态")
    private String pingStatus;

    @ExcelProperty("ping次数")
    private Integer pingCnt;

    @ExcelProperty("丢包率")
    private String lossRate;

    @ExcelProperty("检测方式")
    private String source;

    @ExcelProperty("失败原因")
    @ContentFontStyle(color = 10)
    private String failReason;
}