package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.bytearray.ByteArrayImageConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 设备监测结果导入信息 Response VO")
@Data
@ExcelIgnoreUnannotated
@ContentRowHeight(120)
public class TrafficMonitorResultsImportDetailExtRespVO extends TrafficMonitorResultsImportDetailRespVO {
    @Schema(description = "杆件编号")
    private String memberName;

    @Schema(description = "是否在保")
    private String underWarranty;

    @Schema(description = "卡口编号")
    @ExcelProperty(value = "卡口编号", index = 2)
    private String kkbh;

    @Schema(description = "电警类型")
    @ExcelProperty(value = "电警类型", index = 3)
    private String djType;

    @ExcelProperty(value = "设备标签", index = 5)
    private String equLabel;

    @ExcelProperty(value = "所属区域", converter = DictConvert.class)
    @DictFormat("geo_info")
    private String unit;

    @ExcelProperty(value = "图片", converter = ByteArrayImageConverter.class, index = 7)
    @ColumnWidth(90)
    private byte[] imageBytes;
}