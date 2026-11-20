package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产二维码信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsQrcodePrintRespVO {

    @Schema(description = "道路名称", example = "芋艿")
    @ExcelProperty("道路名称")
    private String raodName;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "序列号前缀")
    private String serialPrefix;

    @Schema(description = "序列号")
    @ExcelProperty("序列号")
    private Integer serialNum;

    @Schema(description = "二维码文件")
    private String qrcodeFile;
}