package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产二维码信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsQrcodePageReqVO extends PageParam {

    @Schema(description = "所属单位：1-交警；2-公安")
    private String affiliatedUnit;

    @Schema(description = "道路ID", example = "12895")
    private String roadId;

    @Schema(description = "道路名称", example = "芋艿")
    private String raodName;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "生成记录ID", example = "27559")
    private String genRecordId;

    @Schema(description = "资产类型", example = "2")
    private String opsType;

    @Schema(description = "序列号前缀")
    private String serialPrefix;

    @Schema(description = "序列号")
    private Integer serialNum;

    @Schema(description = "二维码文件")
    private String qrcodeFile;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}