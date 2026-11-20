package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产二维码信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsQrcodeRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11302")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "道路ID", example = "12895")
    @ExcelProperty("道路ID")
    private String roadId;

    @Schema(description = "道路名称", example = "芋艿")
    @ExcelProperty("道路名称")
    private String raodName;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "生成记录ID", example = "27559")
    @ExcelProperty("生成记录ID")
    private String genRecordId;

    @Schema(description = "资产类型", example = "2")
    @ExcelProperty("资产类型")
    private String opsType;

    @Schema(description = "序列号前缀")
    private String serialPrefix;

    @Schema(description = "序列号")
    @ExcelProperty("序列号")
    private Integer serialNum;

    @Schema(description = "二维码文件")
    private String qrcodeFile;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}