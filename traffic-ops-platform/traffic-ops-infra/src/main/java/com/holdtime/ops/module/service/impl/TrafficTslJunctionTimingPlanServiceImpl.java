package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslJunctionTimingPlanService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionTimingPlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionTimingPlanMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控交叉口配时方案信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslJunctionTimingPlanServiceImpl implements TrafficTslJunctionTimingPlanService {

    @Resource
    private TrafficTslJunctionTimingPlanMapper tslJunctionTimingPlanMapper;

    @Override
    public String createTslJunctionTimingPlan(TrafficTslJunctionTimingPlanSaveReqVO createReqVO) {
        // 插入
        TrafficTslJunctionTimingPlanDO tslJunctionTimingPlan = BeanUtils.toBean(createReqVO, TrafficTslJunctionTimingPlanDO.class);
        tslJunctionTimingPlanMapper.insert(tslJunctionTimingPlan);
        // 返回
        return tslJunctionTimingPlan.getId();
    }

    @Override
    public void updateTslJunctionTimingPlan(TrafficTslJunctionTimingPlanSaveReqVO updateReqVO) {
        // 校验存在
        validateTslJunctionTimingPlanExists(updateReqVO.getId());
        // 更新
        TrafficTslJunctionTimingPlanDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslJunctionTimingPlanDO.class);
        tslJunctionTimingPlanMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslJunctionTimingPlan(String id) {
        // 校验存在
        validateTslJunctionTimingPlanExists(id);
        // 删除
        tslJunctionTimingPlanMapper.deleteById(id);
    }

    private void validateTslJunctionTimingPlanExists(String id) {
        if (tslJunctionTimingPlanMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_JUNCTION_TIMING_PLAN_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslJunctionTimingPlanDO getTslJunctionTimingPlan(String id) {
        return tslJunctionTimingPlanMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslJunctionTimingPlanDO> getTslJunctionTimingPlanPage(TrafficTslJunctionTimingPlanPageReqVO pageReqVO) {
        return tslJunctionTimingPlanMapper.selectPage(pageReqVO);
    }

}