package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslJunctionDayPlanInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionDayPlanInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionDayPlanInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控交叉口日方案信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslJunctionDayPlanInfoServiceImpl implements TrafficTslJunctionDayPlanInfoService {

    @Resource
    private TrafficTslJunctionDayPlanInfoMapper tslJunctionDayPlanInfoMapper;

    @Override
    public String createTslJunctionDayPlanInfo(TrafficTslJunctionDayPlanInfoSaveReqVO createReqVO) {
        // 插入
        TrafficTslJunctionDayPlanInfoDO tslJunctionDayPlanInfo = BeanUtils.toBean(createReqVO, TrafficTslJunctionDayPlanInfoDO.class);
        tslJunctionDayPlanInfoMapper.insert(tslJunctionDayPlanInfo);
        // 返回
        return tslJunctionDayPlanInfo.getId();
    }

    @Override
    public void updateTslJunctionDayPlanInfo(TrafficTslJunctionDayPlanInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateTslJunctionDayPlanInfoExists(updateReqVO.getId());
        // 更新
        TrafficTslJunctionDayPlanInfoDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslJunctionDayPlanInfoDO.class);
        tslJunctionDayPlanInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslJunctionDayPlanInfo(String id) {
        // 校验存在
        validateTslJunctionDayPlanInfoExists(id);
        // 删除
        tslJunctionDayPlanInfoMapper.deleteById(id);
    }

    private void validateTslJunctionDayPlanInfoExists(String id) {
        if (tslJunctionDayPlanInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_JUNCTION_DAY_PLAN_INFO_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslJunctionDayPlanInfoDO getTslJunctionDayPlanInfo(String id) {
        return tslJunctionDayPlanInfoMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslJunctionDayPlanInfoDO> getTslJunctionDayPlanInfoPage(TrafficTslJunctionDayPlanInfoPageReqVO pageReqVO) {
        return tslJunctionDayPlanInfoMapper.selectPage(pageReqVO);
    }

}