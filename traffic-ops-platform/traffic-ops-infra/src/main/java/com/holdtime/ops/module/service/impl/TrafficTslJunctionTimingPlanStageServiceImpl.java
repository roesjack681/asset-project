package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslJunctionTimingPlanStageService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionTimingPlanStageDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionTimingPlanStageMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控交叉口配时方案阶段信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslJunctionTimingPlanStageServiceImpl implements TrafficTslJunctionTimingPlanStageService {

    @Resource
    private TrafficTslJunctionTimingPlanStageMapper tslJunctionTimingPlanStageMapper;

    @Override
    public String createTslJunctionTimingPlanStage(TrafficTslJunctionTimingPlanStageSaveReqVO createReqVO) {
        // 插入
        TrafficTslJunctionTimingPlanStageDO tslJunctionTimingPlanStage = BeanUtils.toBean(createReqVO, TrafficTslJunctionTimingPlanStageDO.class);
        tslJunctionTimingPlanStageMapper.insert(tslJunctionTimingPlanStage);
        // 返回
        return tslJunctionTimingPlanStage.getId();
    }

    @Override
    public void updateTslJunctionTimingPlanStage(TrafficTslJunctionTimingPlanStageSaveReqVO updateReqVO) {
        // 校验存在
        validateTslJunctionTimingPlanStageExists(updateReqVO.getId());
        // 更新
        TrafficTslJunctionTimingPlanStageDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslJunctionTimingPlanStageDO.class);
        tslJunctionTimingPlanStageMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslJunctionTimingPlanStage(String id) {
        // 校验存在
        validateTslJunctionTimingPlanStageExists(id);
        // 删除
        tslJunctionTimingPlanStageMapper.deleteById(id);
    }

    private void validateTslJunctionTimingPlanStageExists(String id) {
        if (tslJunctionTimingPlanStageMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_JUNCTION_TIMING_PLAN_STAGE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslJunctionTimingPlanStageDO getTslJunctionTimingPlanStage(String id) {
        return tslJunctionTimingPlanStageMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslJunctionTimingPlanStageDO> getTslJunctionTimingPlanStagePage(TrafficTslJunctionTimingPlanStagePageReqVO pageReqVO) {
        return tslJunctionTimingPlanStageMapper.selectPage(pageReqVO);
    }

}