package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.PoliceMonitorPingDetailDO;
import com.holdtime.ops.module.model.vo.PoliceMonitorPingDetailExtRespVO;

import java.time.LocalDateTime;

/**
 * 公安设备监测结果 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorPingExtService {
    /**
     * ping公安设备
     * @param equId ping对象的设备id
     * @return ping监控结果
     */
    PoliceMonitorPingDetailExtRespVO pingPoliceOpsElectricPolice(String equId, String source);

    /**
     * ping公安设备
     * @param policeOpsElectricPoliceDO 电警信息
     * @return ping监控结果
     */
    PoliceMonitorPingDetailExtRespVO pingPoliceOpsElectricPolice(PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO, LocalDateTime occurrenceTime, String source);

    /**
     * 分析公安通断情况
     * @param equId 设备ID
     */
    void analysisMonitorPing(String equId);

    /**
     * 分析公安全部设备通断情况
     */
    void analysisMonitorPing();

    /**
     * 插入ping详情 (用于批量处理优化)
     * @param pingDetail ping详情
     */
    void insertPingDetail(PoliceMonitorPingDetailDO pingDetail);

    /**
     * 发送ping结果到Kafka (用于批量处理优化)
     * @param pingDetail ping详情
     */
    void sendPingToKafka(PoliceMonitorPingDetailDO pingDetail);
}