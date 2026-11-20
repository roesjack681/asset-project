package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 电警分页 Request VO")
@Data
@ToString(callSuper = true)
public class TrafficOpsSmsElectricPolicePageReqVO{

    @Schema(description = "发送类型：1重点考核视频；2重点考核卡口", example = "1")
    private String sendType;


    @Schema(description = "设备信息")
    private List<TrafficOpsSmsElectricPoliceRespVO> electricPoliceList;
}