package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.TrafficTslJunctionDayPlanDetailRespVO;
import com.holdtime.ops.module.model.vo.TrafficTslJunctionPlanInfoExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficTslJunctionTimingPlanStageRespVO;

import java.util.List;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficTslTimingPlanExtService {

    /**
     * 根据设备信息查询周方案
     * @param signalId 信号机ID
     * @return 周方案信息
     */
    TrafficTslJunctionPlanInfoExtRespVO queryJunctionPlanInfo(String signalId);

    /**
     * 查询日方案详细信息
     * @param junctionId 交叉口ID
     * @param dayPlansId 日方案号
     * @return 日方案信息
     */
    List<TrafficTslJunctionDayPlanDetailRespVO> queryJunctionDayPlanList(String junctionId, String dayPlansId);


    /**
     * 查询配时阶段信息
     * @param junctionId 信控设备ID
     * @param planId 交叉口ID
     * @return 配时阶段信息
     */
    List<TrafficTslJunctionTimingPlanStageRespVO> queryJunctionTimeStageList(String junctionId, String planId);
}