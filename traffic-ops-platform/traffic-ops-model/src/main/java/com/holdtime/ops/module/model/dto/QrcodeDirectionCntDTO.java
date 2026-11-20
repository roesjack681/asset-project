package com.holdtime.ops.module.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 维修排名")
@Data
public class QrcodeDirectionCntDTO {
    @ExcelProperty("方向")
    private String direction;

    @ExcelProperty("生成数量")
    private Integer genCnt;
}
