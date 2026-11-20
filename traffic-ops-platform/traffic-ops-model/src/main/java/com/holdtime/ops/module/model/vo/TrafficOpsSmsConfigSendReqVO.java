package com.holdtime.ops.module.model.vo;

import com.holdtime.ops.module.model.dto.TrafficOpsSmsConfigSendDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Schema(description = "管理后台 - 资产短信发送配置新增/修改 Request VO")
@Data
public class TrafficOpsSmsConfigSendReqVO {

    private List<TrafficOpsSmsConfigSendDTO> sendList;

}