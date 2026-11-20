package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 电动自行车导入分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImportRecordPageReqVO extends PageParam {
    @Schema(description = "导入类型")
    @ExcelProperty("导入类型")
    private String importType;

    @Schema(description = "导入文件名")
    private String fileName;

    @Schema(description = "导入文件")
    private String importFile;

    @Schema(description = "导入记录数")
    private Integer importNum;

    @Schema(description = "成功记录数")
    private Integer sucNum;

    @Schema(description = "失败记录数")
    private Integer failNum;

    @Schema(description = "失败文件")
    private String failFile;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}