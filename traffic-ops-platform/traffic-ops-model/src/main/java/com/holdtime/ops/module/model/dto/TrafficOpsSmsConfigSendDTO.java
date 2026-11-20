package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Schema(description = "管理后台 - 资产短信发送配置新增/修改 Request VO")
@Data
public class TrafficOpsSmsConfigSendDTO {

    @Schema(description = "发送类型：1重点考核视频；2重点考核卡口", example = "1")
    private String sendType;

    @Schema(description = "设备主键")
    private String equId;

}