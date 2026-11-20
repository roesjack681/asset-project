package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 维保材料信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppTrafficOpsMaintenanceFilesPageReqVO extends PageParam {

    @Schema(description = "维保主键", example = "29809")
    private String maintenanceId;

    @Schema(description = "维保进度id", example = "8674")
    private String progressId;

    @Schema(description = "报修类型：上报；维修", example = "1")
    private String repairType;

    @Schema(description = "材料类型：图片；音频；视频", example = "1")
    private String fileType;

    @Schema(description = "材料url", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}