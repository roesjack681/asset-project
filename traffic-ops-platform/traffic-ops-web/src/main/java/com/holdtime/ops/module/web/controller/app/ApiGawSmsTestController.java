package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.component.sms.TpSmsClient;
import com.holdtime.ops.module.model.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "开放API - 终端位置,供设备调用")
@RestController
@RequestMapping("/openapi/ops/sms-test")
@Validated
public class ApiGawSmsTestController {

    @PostMapping("/sendGawSms")
    @Operation(summary = "同步设备监测结果")
    public CommonResult<String> createMonitorResults(@Valid @RequestBody TrafficGawSmsTestReqVO smsTestReqVO) {
        String smsContent = "重点考核设备掉线，请及时查看。详情：http://ops.nmholdtime.com/trafficOpsWx/app-api/s/r?p=eJwz1DU0NTAyMDA0B2JjIwNTENNcB4ugBaagmYEpNkEzayMsplqaYVULAF3GGR0=";


        String result = TpSmsClient.getInstance().sendSmsMsg(smsTestReqVO.getApiUrl(),
                smsTestReqVO.getSerialNum(), smsTestReqVO.getMac(),
                smsTestReqVO.getSystemName(), smsTestReqVO.getSendUserName(),
                smsTestReqVO.getSendUserDept(), smsTestReqVO.getReceivePhone(), smsContent);
        return success(result);
    }

}