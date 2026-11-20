package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.system.dal.dataobject.sms.SmsLogDO;
import cn.iocoder.yudao.module.system.dal.mysql.sms.SmsLogMapper;
import cn.iocoder.yudao.module.system.enums.sms.SmsReceiveStatusEnum;
import cn.iocoder.yudao.module.system.enums.sms.SmsSendStatusEnum;
import com.holdtime.ops.module.conf.OpsProjectEmayConfig;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.Sms4JService;
import com.holdtime.ops.module.utils.emay.EmaySmsUtils;
import com.holdtime.ops.module.utils.emay.SmsSingleReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
@Slf4j
public class Sms4JServiceImpl implements Sms4JService {
    @Resource
    private OpsProjectEmayConfig opsProjectEmayConfig;
    @Resource
    private SmsLogMapper smsLogMapper;


    @Override
    public SmsSingleReqDTO sendNoticeMsg(String phone, String noticeMsgKey) {
        if (StringUtils.isBlank(phone)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        if (StringUtils.isBlank(noticeMsgKey)) {
            noticeMsgKey = "dispatchMsg";
        }

        SmsSingleReqDTO dto = new SmsSingleReqDTO();
        dto.setMobile(phone);
        dto.setContent(opsProjectEmayConfig.getNoticeMsgMap().get(noticeMsgKey));
        String result = sendSmsMsg(dto);

        saveSmsLog(dto, result);
        return dto;
    }

    @Override
    public SmsSingleReqDTO sendCustomNoticeMsg(String phone, String noticeMsg) {
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(noticeMsg)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        SmsSingleReqDTO dto = new SmsSingleReqDTO();
        dto.setMobile(phone);
        dto.setContent(noticeMsg);
        String result = sendSmsMsg(dto);

        saveSmsLog(dto, result);
        return dto;
    }

    private String sendSmsMsg(SmsSingleReqDTO dto) {
        return EmaySmsUtils.sendSingleSms(opsProjectEmayConfig.getHost(), opsProjectEmayConfig.getAppId(), opsProjectEmayConfig.getSecretKey(),
                "UTF-8", true,
                "AES/ECB/PKCS5Padding", opsProjectEmayConfig.getSignName(), dto);
    }

    private void saveSmsLog(SmsSingleReqDTO dto, String result) {
        // 保存短信发送记录
        SmsLogDO smsLogDO = new SmsLogDO();
        smsLogDO.setChannelId(1L);
        smsLogDO.setChannelCode("emay");
        smsLogDO.setTemplateId(1L);
        smsLogDO.setTemplateCode("ops-sms-notice");
        smsLogDO.setTemplateType(2);
        smsLogDO.setTemplateParams(new HashMap<>());
        smsLogDO.setApiTemplateId("EMAY_SYSTEM");
        smsLogDO.setMobile(dto.getMobile());
        smsLogDO.setTemplateContent(dto.getContent());
        if ("SUCCESS".equals(result) ) {
            smsLogDO.setSendStatus(SmsSendStatusEnum.SUCCESS.getStatus());
            smsLogDO.setReceiveStatus(SmsReceiveStatusEnum.SUCCESS.getStatus());
        } else {
            smsLogDO.setSendStatus(SmsSendStatusEnum.FAILURE.getStatus());
            smsLogDO.setReceiveStatus(SmsReceiveStatusEnum.FAILURE.getStatus());
        }
        smsLogDO.setSendTime(DateUtil.toLocalDateTime(new Date()));
        smsLogMapper.insert(smsLogDO);
    }
}
