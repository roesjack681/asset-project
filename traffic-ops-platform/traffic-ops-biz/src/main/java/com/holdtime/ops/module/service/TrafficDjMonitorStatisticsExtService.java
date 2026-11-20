package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.TrafficDjMonitorStatisticsExtSaveReqVO;

import javax.validation.Valid;

/**
 * 监测电警设备统计 Service 接口
 *
 * @author 管理员
 */
public interface TrafficDjMonitorStatisticsExtService {

    /**
     * 同步电警统计信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String syncElectricPoliceMonitorStatistics(@Valid TrafficDjMonitorStatisticsExtSaveReqVO createReqVO);
}