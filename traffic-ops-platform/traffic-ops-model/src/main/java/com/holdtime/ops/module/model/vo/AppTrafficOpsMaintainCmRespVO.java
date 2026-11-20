package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 设备维保耗材 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppTrafficOpsMaintainCmRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30666")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "维修进度主键", example = "844")
    @ExcelProperty("维修进度主键")
    private String progressId;

    @Schema(description = "耗材种类")
    @ExcelProperty("耗材种类")
    private String cmSort;

    @Schema(description = "耗材数量")
    @ExcelProperty("耗材数量")
    private String cmNum;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}