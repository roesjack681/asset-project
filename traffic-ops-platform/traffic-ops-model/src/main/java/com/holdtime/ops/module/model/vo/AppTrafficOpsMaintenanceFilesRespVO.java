package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 维保材料信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppTrafficOpsMaintenanceFilesRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4038")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "维保主键", example = "29809")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "维保进度id", example = "8674")
    @ExcelProperty("维保进度id")
    private String progressId;

    @Schema(description = "报修类型：上报；维修", example = "1")
    @ExcelProperty("报修类型：上报；维修")
    private String repairType;

    @Schema(description = "材料类型：图片；音频；视频", example = "1")
    @ExcelProperty("材料类型：图片；音频；视频")
    private String fileType;

    @Schema(description = "材料url", example = "https://www.iocoder.cn")
    @ExcelProperty("材料url")
    private String fileUrl;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}