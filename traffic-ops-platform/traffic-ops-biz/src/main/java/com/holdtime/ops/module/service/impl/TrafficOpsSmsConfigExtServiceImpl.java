package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.component.sms.TpSmsClient;
import com.holdtime.ops.module.conf.OpsProjectGawSmsConfig;
import com.holdtime.ops.module.entity.TrafficOpsSmsConfigDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSmsConfigMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.dto.TrafficOpsSmsConfigSendDTO;
import com.holdtime.ops.module.model.vo.TrafficOpsSmsConfigSendReqVO;
import com.holdtime.ops.module.service.TrafficOpsSmsConfigExtService;
import com.holdtime.ops.module.utils.StringEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 资产短信发送配置 Service 实现类
 *
 * @author 杨丽
 */
@Service
@Validated
public class TrafficOpsSmsConfigExtServiceImpl implements TrafficOpsSmsConfigExtService {

    @Resource
    private TrafficOpsSmsConfigMapper opsSmsConfigMapper;
    @Resource
    private OpsProjectGawSmsConfig opsProjectGawSmsConfig;

    @Override
    public String sendSms(TrafficOpsSmsConfigSendReqVO sendReqVO) {
        if (sendReqVO == null || CollectionUtils.isEmpty(sendReqVO.getSendList())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        //根据传递的参数查询组织短信内容
        List<TrafficOpsSmsConfigSendDTO> sendList = sendReqVO.getSendList();
        List<String> zdkhspList = sendList.stream().filter(dto -> OpsConstants.STRING_ONE.equals(dto.getSendType())).map(TrafficOpsSmsConfigSendDTO::getEquId).collect(Collectors.toList());
        List<String> zdkhkkList = sendList.stream().filter(dto -> OpsConstants.STRING_TWO.equals(dto.getSendType())).map(TrafficOpsSmsConfigSendDTO::getEquId).collect(Collectors.toList());

        //将用户根据接收类型分为三组
        LambdaQueryWrapperX<TrafficOpsSmsConfigDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsSmsConfigDO::getReceiveStatus, "on");
        List<TrafficOpsSmsConfigDO> opsSmsConfigDOList = opsSmsConfigMapper.selectList();
        if (opsSmsConfigDOList == null || opsSmsConfigDOList.isEmpty()) {
            throw exception(OpsErrorCodeConstants.OPS_SMS_CONFIG_NOT_EXISTS);
        }

        Map<String, List<TrafficOpsSmsConfigDO>> grouped = opsSmsConfigDOList.stream()
                .collect(Collectors.groupingBy(item ->
                        "1".equals(item.getSendType()) ? "1" :
                                "2".equals(item.getSendType()) ? "2" : "other"));

        List<TrafficOpsSmsConfigDO> userList1 = grouped.getOrDefault("1", new ArrayList<>());
        List<TrafficOpsSmsConfigDO> userList2 = grouped.getOrDefault("2", new ArrayList<>());
        List<TrafficOpsSmsConfigDO> userList3 = grouped.getOrDefault("other", new ArrayList<>());

        String pattern = "重点考核设备掉线，请及时查看。详情：http://ops.nmholdtime.com/trafficOpsWx/app-api/s/r?p={0}";
        String zdkhspParam = "";
        String zdkhkkParam = "";
        if (!CollectionUtils.isEmpty(zdkhspList)) {
            zdkhspParam = OpsConstants.STRING_ONE + "-" + StringUtils.join(zdkhspList, ",");
            String smsContent = MessageFormat.format(pattern, StringEncoder.encode(zdkhspParam));
            for (TrafficOpsSmsConfigDO smsConfig : userList1) {
                TpSmsClient.getInstance().sendSmsMsg(opsProjectGawSmsConfig.getApiUrl(),
                        opsProjectGawSmsConfig.getSerialNum(), opsProjectGawSmsConfig.getMac(),
                        opsProjectGawSmsConfig.getSystemName(), opsProjectGawSmsConfig.getSendUserName(),
                        opsProjectGawSmsConfig.getSendUserDept(), smsConfig.getReceivePhone(), smsContent);
            }
        }

        if (!CollectionUtils.isEmpty(zdkhkkList)) {
            zdkhkkParam = OpsConstants.STRING_TWO + "-" + StringUtils.join(zdkhkkList, ",");
            String smsContent = MessageFormat.format(pattern, StringEncoder.encode(zdkhkkParam));
            for (TrafficOpsSmsConfigDO smsConfig : userList2) {
                TpSmsClient.getInstance().sendSmsMsg(opsProjectGawSmsConfig.getApiUrl(),
                        opsProjectGawSmsConfig.getSerialNum(), opsProjectGawSmsConfig.getMac(),
                        opsProjectGawSmsConfig.getSystemName(), opsProjectGawSmsConfig.getSendUserName(),
                        opsProjectGawSmsConfig.getSendUserDept(), smsConfig.getReceivePhone(), smsContent);
            }
        }

        if (!CollectionUtils.isEmpty(zdkhspList) && !CollectionUtils.isEmpty(zdkhkkList)) {
            String allParam = zdkhspParam + ";" + zdkhkkParam;
            String smsContent = MessageFormat.format(pattern, StringEncoder.encode(allParam));
            for (TrafficOpsSmsConfigDO smsConfig : userList3) {
                TpSmsClient.getInstance().sendSmsMsg(opsProjectGawSmsConfig.getApiUrl(),
                        opsProjectGawSmsConfig.getSerialNum(), opsProjectGawSmsConfig.getMac(),
                        opsProjectGawSmsConfig.getSystemName(), opsProjectGawSmsConfig.getSendUserName(),
                        opsProjectGawSmsConfig.getSendUserDept(), smsConfig.getReceivePhone(), smsContent);
            }
        }
        return "success";
    }

}