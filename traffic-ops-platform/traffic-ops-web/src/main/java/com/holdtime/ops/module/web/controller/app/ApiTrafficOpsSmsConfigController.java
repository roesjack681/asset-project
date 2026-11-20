package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.model.vo.TrafficOpsSmsConfigSendReqVO;
import com.holdtime.ops.module.service.TrafficOpsSmsConfigExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 资产短信发送配置")
@RestController
@RequestMapping("openapi/ops/ops-sms-config")
@Validated
public class ApiTrafficOpsSmsConfigController {

    @Resource
    private TrafficOpsSmsConfigExtService opsSmsConfigExtService;


    @PostMapping("/sendSms")
    @Operation(summary = "调用配置人员发送通知信息")
    public CommonResult<String> sendSms(@Valid @RequestBody TrafficOpsSmsConfigSendReqVO sendReqVO) {
        return success(opsSmsConfigExtService.sendSms(sendReqVO));
    }

}