package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信控区域信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficTslRegionInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12918")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "区域名称", example = "赵六")
    @ExcelProperty("区域名称")
    private String regionName;

    @Schema(description = "tcsID", example = "2034")
    @ExcelProperty("tcsID")
    private String tcsId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}