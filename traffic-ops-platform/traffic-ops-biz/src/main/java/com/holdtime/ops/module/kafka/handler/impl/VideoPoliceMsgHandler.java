package com.holdtime.ops.module.kafka.handler.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.holdtime.ops.module.conf.OpsProjectSpwConfig;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.kafka.message.VideoNetBoundaryMessage;
import com.holdtime.ops.module.kafka.producer.BoundaryMsgProducer;
import com.holdtime.ops.module.utils.PingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Component
@Slf4j
public class VideoPoliceMsgHandler {
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private ObjectProvider<BoundaryMsgProducer> boundaryMsgProducerObjectProvider;
    @Resource
    private OpsProjectSpwConfig opsProjectSpwConfig;

    public void handlerHealthCheckPoliceOpsElectricPoliceMsg(VideoNetBoundaryMessage message) {
        if (message == null || StringUtils.isBlank(message.getMsgData())) {
            log.warn("收到空消息");
            return;
        }

        PublicEquipOnOffCheckResultDO publicEquipOnOffCheckResultDO = JSONUtil.toBean(message.getMsgData(), PublicEquipOnOffCheckResultDO.class);

        //根据消息内容获取电警信息
        PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(publicEquipOnOffCheckResultDO.getEquId());
        if (policeOpsElectricPoliceDO == null) {
            log.warn("电警信息不存在，id: {}", message.getMsgData());
            publicEquipOnOffCheckResultDO.setPingStatus("设备不存在！");
            sendBoundaryMessage(publicEquipOnOffCheckResultDO);
            return;
        }
        publicEquipOnOffCheckResultDO.setIpAddr(policeOpsElectricPoliceDO.getIpAddr());
        publicEquipOnOffCheckResultDO.setEquCode(policeOpsElectricPoliceDO.getEquCode());
        publicEquipOnOffCheckResultDO.setOccurrenceTime(DateUtil.toLocalDateTime(new Date()));
        try {
            PingUtil.PingResult pingResult = PingUtil.pingWithLossRate(policeOpsElectricPoliceDO.getIpAddr(), 1000, 10);
            publicEquipOnOffCheckResultDO.setPingStatus(pingResult.isReachable() ? "正常" : "异常");
            publicEquipOnOffCheckResultDO.setPingCnt(10);
            publicEquipOnOffCheckResultDO.setLossRate(BigDecimal.valueOf(pingResult.getLossRate()));
            if (!OpsConstants.STRING_ONE.equals(publicEquipOnOffCheckResultDO.getCheckType())) {
                sendBoundaryMessage(publicEquipOnOffCheckResultDO);
                return;
            }
            if (pingResult.isReachable()) {
                // ping通，拉流抓图片
                if (StringUtils.isBlank(policeOpsElectricPoliceDO.getEquCode())) {
                    log.warn("设备编码为空，无法进行巡检，equId: {}", policeOpsElectricPoliceDO.getId());
                    publicEquipOnOffCheckResultDO.setVideoStatus("设备通道编码为空");
                    sendBoundaryMessage(publicEquipOnOffCheckResultDO);
                    return;
                }

                performVideoInspection(policeOpsElectricPoliceDO, publicEquipOnOffCheckResultDO);
            } else {
                publicEquipOnOffCheckResultDO.setVideoStatus("网络不通，未进行拉流检查");
                sendBoundaryMessage(publicEquipOnOffCheckResultDO);
            }

        } catch (Exception e) {
            log.error("处理健康检查消息失败, equId: {}", policeOpsElectricPoliceDO.getId(), e);
            throw new RuntimeException("健康检查消息处理失败", e);
        }
    }

    private void performVideoInspection(PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO,
                                       PublicEquipOnOffCheckResultDO publicEquipOnOffCheckResultDO) {
        try {
            String inspectionUrl = opsProjectSpwConfig.getWvpHost() + "/api/v1/inspection/check/" + policeOpsElectricPoliceDO.getEquCode();
            HttpResponse response = HttpUtil.createGet(inspectionUrl)
                    .timeout(60000)
                    .execute();

            if (!response.isOk()) {
                log.warn("设备巡检接口调用失败，deviceId: {}, status: {}", policeOpsElectricPoliceDO.getEquCode(), response.getStatus());
                publicEquipOnOffCheckResultDO.setVideoStatus("流媒体接口调用失败");
                sendBoundaryMessage(publicEquipOnOffCheckResultDO);
                return;
            }

            String responseBody = response.body();
            JSONObject result = JSONUtil.parseObj(responseBody);

            if (result.getInt("code") != 0) {
                log.warn("设备巡检失败，deviceId: {}, msg: {}", policeOpsElectricPoliceDO.getEquCode(), result.getStr("msg"));
                publicEquipOnOffCheckResultDO.setVideoStatus("巡检失败：" + result.getStr("msg"));
                sendBoundaryMessage(publicEquipOnOffCheckResultDO);
                return;
            }

            JSONObject data = result.getJSONObject("data");
            String status = data.getStr("status");
            String snapBase64 = data.getStr("snapBase64");

            // 设置视频检测结果
            if ("ON".equals(status)) {
                publicEquipOnOffCheckResultDO.setVideoStatus("正常");
                if (StringUtils.isNotBlank(snapBase64)) {
                    publicEquipOnOffCheckResultDO.setResultImg(snapBase64);
                }
            } else {
                publicEquipOnOffCheckResultDO.setVideoStatus("拉流失败：" + data.getStr("msg"));
            }

            sendBoundaryMessage(publicEquipOnOffCheckResultDO);
        } catch (Exception e) {
            log.error("设备巡检异常，deviceId: {}", policeOpsElectricPoliceDO.getEquCode(), e);
            publicEquipOnOffCheckResultDO.setVideoStatus("巡检异常：" + e.getMessage());
            sendBoundaryMessage(publicEquipOnOffCheckResultDO);
        }
    }

    private void sendBoundaryMessage(PublicEquipOnOffCheckResultDO publicEquipOnOffCheckResultDO) {
        boundaryMsgProducerObjectProvider.ifAvailable(boundaryMsgProducer ->
                boundaryMsgProducer.sendMessageFromVideoToInternet(
                        "POLICE_EQU_CONFIRM_RESULT",
                        "HEALTH_CHECK_RESULT",
                        JSONUtil.toJsonStr(publicEquipOnOffCheckResultDO)
                )
        );
    }

}
