package com.holdtime.ops.module.job.spw;

import cn.hutool.core.lang.UUID;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.holdtime.ops.module.conf.OpsProjectSpwConfig;
import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.HttpStatusEnum;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.dto.TrafficOpsSmsConfigSendDTO;
import com.holdtime.ops.module.model.vo.TrafficOpsSmsConfigSendReqVO;
import com.holdtime.ops.module.service.TrafficEquipPingMonitorResultExtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TrafficMonitorPingExecJob implements JobHandler {
    @Resource
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    private TrafficEquipPingMonitorResultExtService trafficEquipPingMonitorResultExtService;
    @Resource
    private OpsProjectSpwConfig opsProjectSpwConfig;

    @Override
    public String execute(String param) {
        scheduledPingEvent();
        return "分析完成！";
    }

    private void scheduledPingEvent() {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getEquLable, "重点考核视频");
        lambdaQueryWrapperX.or().like(TrafficOpsElectricPoliceDO::getEquLable, "重点考核卡口");
        List<TrafficOpsElectricPoliceDO> opsElectricPoliceDOList = trafficOpsElectricPoliceMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(opsElectricPoliceDOList)) {
            return;
        }
        List<TrafficOpsElectricPoliceDO> zdkhspList = opsElectricPoliceDOList.stream().filter(electricPoliceDO -> electricPoliceDO.getEquLable().contains("重点考核视频")).collect(Collectors.toList());
        List<TrafficOpsElectricPoliceDO> zdkhkkList = opsElectricPoliceDOList.stream().filter(electricPoliceDO -> electricPoliceDO.getEquLable().contains("重点考核卡口")).collect(Collectors.toList());

        List<TrafficOpsSmsConfigSendDTO> sendReqVOList = new ArrayList<>();

        String batchNum = UUID.randomUUID().toString(true);
        zdkhspList.forEach(zdkhsp -> {
            TrafficEquipPingMonitorResultDO pingMonitorResultDO = trafficEquipPingMonitorResultExtService.pingTrafficOpsElectricPolice(zdkhsp, null, OpsConstants.STRING_ONE, batchNum, 10);
            if (!"正常".equals(pingMonitorResultDO.getPingStatus())) {
                sendReqVOList.add(getSendDTO(OpsConstants.STRING_ONE, pingMonitorResultDO.getEquId()));
            }
        });

        zdkhkkList.forEach(zdkhkk -> {
            TrafficEquipPingMonitorResultDO pingMonitorResultDO = trafficEquipPingMonitorResultExtService.pingTrafficOpsElectricPolice(zdkhkk, null, OpsConstants.STRING_ONE, batchNum, 10);
            if (!"正常".equals(pingMonitorResultDO.getPingStatus())) {
                sendReqVOList.add(getSendDTO(OpsConstants.STRING_TWO, pingMonitorResultDO.getEquId()));
            }
        });

        log.info("有{}个设备需要发送短信", sendReqVOList.size());
        if  (!CollectionUtils.isEmpty(sendReqVOList)) {
            // 调用公安网接口，推送检测结果
            TrafficOpsSmsConfigSendReqVO sendReqVO = new TrafficOpsSmsConfigSendReqVO();
            sendReqVO.setSendList(sendReqVOList);
            String apiUrl = opsProjectSpwConfig.getGawHost() + "openapi/ops/ops-sms-config/sendSms";
            HttpResult httpResult = OkHttps.sync(apiUrl).bodyType("application/json").setBodyPara(sendReqVO).post();
            HttpStatusEnum httpStatusEnum = HttpStatusEnum.getHttpStatus(httpResult.getStatus());
            if (httpStatusEnum != null) {
                log.error(httpStatusEnum.getDesc());
            }
            log.info(httpResult.getBody().toString());
        }
    }

    private TrafficOpsSmsConfigSendDTO getSendDTO(String sendType, String equId) {
        TrafficOpsSmsConfigSendDTO sendDTO = new TrafficOpsSmsConfigSendDTO();
        sendDTO.setSendType(sendType);
        sendDTO.setEquId(equId);
        return sendDTO;
    }
}
