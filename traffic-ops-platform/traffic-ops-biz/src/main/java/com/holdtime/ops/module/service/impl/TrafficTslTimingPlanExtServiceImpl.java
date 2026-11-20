package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.entity.TrafficTslJunctionDayPlanDetailDO;
import com.holdtime.ops.module.entity.TrafficTslJunctionDeviceInfoDO;
import com.holdtime.ops.module.entity.TrafficTslJunctionPlanInfoDO;
import com.holdtime.ops.module.entity.TrafficTslJunctionTimingPlanStageDO;
import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionDayPlanDetailMapper;
import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionDeviceInfoMapper;
import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionPlanInfoMapper;
import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionTimingPlanStageMapper;
import com.holdtime.ops.module.model.vo.TrafficTslJunctionDayPlanDetailRespVO;
import com.holdtime.ops.module.model.vo.TrafficTslJunctionPlanInfoExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficTslJunctionTimingPlanStageRespVO;
import com.holdtime.ops.module.service.TrafficTslTimingPlanExtService;
import org.apache.tika.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Validated
public class TrafficTslTimingPlanExtServiceImpl implements TrafficTslTimingPlanExtService {
    @Resource
    private TrafficTslJunctionDeviceInfoMapper trafficTslJunctionDeviceInfoMapper;
    @Resource
    private TrafficTslJunctionPlanInfoMapper trafficTslJunctionPlanInfoMapper;
    @Resource
    private TrafficTslJunctionDayPlanDetailMapper trafficTslJunctionDayPlanDetailMapper;
    @Resource
    private TrafficTslJunctionTimingPlanStageMapper trafficTslJunctionTimingPlanStageMapper;

    @Override
    public TrafficTslJunctionPlanInfoExtRespVO queryJunctionPlanInfo(String signalId) {
        LambdaQueryWrapper<TrafficTslJunctionDeviceInfoDO> deviceInfoDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        deviceInfoDOLambdaQueryWrapper.eq(TrafficTslJunctionDeviceInfoDO::getSignalId, signalId);
        List<TrafficTslJunctionDeviceInfoDO> deviceInfoDOList = trafficTslJunctionDeviceInfoMapper.selectList(deviceInfoDOLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(deviceInfoDOList)) {
            return null;
        }
        TrafficTslJunctionDeviceInfoDO deviceInfoDO = deviceInfoDOList.get(0);
        // 查询周方案信息
        LambdaQueryWrapper<TrafficTslJunctionPlanInfoDO> planInfoDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planInfoDOLambdaQueryWrapper.eq(TrafficTslJunctionPlanInfoDO::getControllerId, deviceInfoDO.getId());
        List<TrafficTslJunctionPlanInfoDO> junctionPlanInfoDOList = trafficTslJunctionPlanInfoMapper.selectList(planInfoDOLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(junctionPlanInfoDOList)) {
            return null;
        }

        TrafficTslJunctionPlanInfoExtRespVO respVO = BeanUtils.toBean(junctionPlanInfoDOList.get(0), TrafficTslJunctionPlanInfoExtRespVO.class);
        int week = DateUtil.dayOfWeek(new Date()) - 1;
        if (week == 0) {
            week = 7;
        }
        respVO.setWeek(week);

        return respVO;
    }

    @Override
    public List<TrafficTslJunctionDayPlanDetailRespVO> queryJunctionDayPlanList(String junctionId, String dayPlansId) {
        if (StringUtils.isBlank(junctionId) || StringUtils.isBlank(dayPlansId)) {
            return null;
        }
        LambdaQueryWrapper<TrafficTslJunctionDayPlanDetailDO> dayPlanDetailDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dayPlanDetailDOLambdaQueryWrapper.exists("select 1 from traffic_tsl_junction_day_plan_info d where traffic_tsl_junction_day_plan_detail.day_plans_id = d.id and d.junction_id = '"+ junctionId + "' and d.day_plans_id = '" + dayPlansId + "'");
        dayPlanDetailDOLambdaQueryWrapper.orderByAsc(TrafficTslJunctionDayPlanDetailDO::getPlanHour);
        dayPlanDetailDOLambdaQueryWrapper.orderByAsc(TrafficTslJunctionDayPlanDetailDO::getPlanMinute);
        dayPlanDetailDOLambdaQueryWrapper.orderByAsc(TrafficTslJunctionDayPlanDetailDO::getId);
        List<TrafficTslJunctionDayPlanDetailDO> dayPlanDetailDOList = trafficTslJunctionDayPlanDetailMapper.selectList(dayPlanDetailDOLambdaQueryWrapper);
        return BeanUtils.toBean(dayPlanDetailDOList, TrafficTslJunctionDayPlanDetailRespVO.class);
    }

    @Override
    public List<TrafficTslJunctionTimingPlanStageRespVO> queryJunctionTimeStageList(String junctionId, String planId) {
        if (StringUtils.isBlank(junctionId) || StringUtils.isBlank(planId)) {
            return null;
        }
        LambdaQueryWrapper<TrafficTslJunctionTimingPlanStageDO> stageDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        stageDOLambdaQueryWrapper.exists("SELECT 1 FROM traffic_tsl_junction_timing_plan tp WHERE tp.id = traffic_tsl_junction_timing_plan_stage.plan_id AND tp.junction_id = '" + junctionId + "' AND plan_id = '" + planId + "'");
        stageDOLambdaQueryWrapper.orderByAsc(TrafficTslJunctionTimingPlanStageDO::getStageId);
        List<TrafficTslJunctionTimingPlanStageDO> stageDOList = trafficTslJunctionTimingPlanStageMapper.selectList(stageDOLambdaQueryWrapper);
        return BeanUtils.toBean(stageDOList, TrafficTslJunctionTimingPlanStageRespVO.class);
    }
}
