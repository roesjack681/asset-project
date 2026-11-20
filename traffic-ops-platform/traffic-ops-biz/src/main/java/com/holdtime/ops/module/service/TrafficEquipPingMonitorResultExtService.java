package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;

import java.time.LocalDateTime;

/**
 * 重点考核设备通断检测 Service 接口
 *
 * @author 管理员
 */
public interface TrafficEquipPingMonitorResultExtService {
    /**
     * ping交警设备
     * @param opsElectricPoliceDO 电警信息
     * @param occurrenceTime 检测时间
     * @param source 来源
     * @param batchNum 批次
     * @param pingCnt ping次数
     * @return 执行结果
     */
    TrafficEquipPingMonitorResultDO pingTrafficOpsElectricPolice(TrafficOpsElectricPoliceDO opsElectricPoliceDO,
                                                                 LocalDateTime occurrenceTime, String source,
                                                                 String batchNum, Integer pingCnt);

}