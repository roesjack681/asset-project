package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslJunctionDayPlanDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionDayPlanDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionDayPlanDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控交叉口日方案详细信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslJunctionDayPlanDetailServiceImpl implements TrafficTslJunctionDayPlanDetailService {

    @Resource
    private TrafficTslJunctionDayPlanDetailMapper tslJunctionDayPlanDetailMapper;

    @Override
    public String createTslJunctionDayPlanDetail(TrafficTslJunctionDayPlanDetailSaveReqVO createReqVO) {
        // 插入
        TrafficTslJunctionDayPlanDetailDO tslJunctionDayPlanDetail = BeanUtils.toBean(createReqVO, TrafficTslJunctionDayPlanDetailDO.class);
        tslJunctionDayPlanDetailMapper.insert(tslJunctionDayPlanDetail);
        // 返回
        return tslJunctionDayPlanDetail.getId();
    }

    @Override
    public void updateTslJunctionDayPlanDetail(TrafficTslJunctionDayPlanDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateTslJunctionDayPlanDetailExists(updateReqVO.getId());
        // 更新
        TrafficTslJunctionDayPlanDetailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslJunctionDayPlanDetailDO.class);
        tslJunctionDayPlanDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslJunctionDayPlanDetail(String id) {
        // 校验存在
        validateTslJunctionDayPlanDetailExists(id);
        // 删除
        tslJunctionDayPlanDetailMapper.deleteById(id);
    }

    private void validateTslJunctionDayPlanDetailExists(String id) {
        if (tslJunctionDayPlanDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_JUNCTION_DAY_PLAN_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslJunctionDayPlanDetailDO getTslJunctionDayPlanDetail(String id) {
        return tslJunctionDayPlanDetailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslJunctionDayPlanDetailDO> getTslJunctionDayPlanDetailPage(TrafficTslJunctionDayPlanDetailPageReqVO pageReqVO) {
        return tslJunctionDayPlanDetailMapper.selectPage(pageReqVO);
    }

}