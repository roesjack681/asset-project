package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslJunctionPlanInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionPlanInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionPlanInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控交叉口方案信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslJunctionPlanInfoServiceImpl implements TrafficTslJunctionPlanInfoService {

    @Resource
    private TrafficTslJunctionPlanInfoMapper tslJunctionPlanInfoMapper;

    @Override
    public String createTslJunctionPlanInfo(TrafficTslJunctionPlanInfoSaveReqVO createReqVO) {
        // 插入
        TrafficTslJunctionPlanInfoDO tslJunctionPlanInfo = BeanUtils.toBean(createReqVO, TrafficTslJunctionPlanInfoDO.class);
        tslJunctionPlanInfoMapper.insert(tslJunctionPlanInfo);
        // 返回
        return tslJunctionPlanInfo.getId();
    }

    @Override
    public void updateTslJunctionPlanInfo(TrafficTslJunctionPlanInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateTslJunctionPlanInfoExists(updateReqVO.getId());
        // 更新
        TrafficTslJunctionPlanInfoDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslJunctionPlanInfoDO.class);
        tslJunctionPlanInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslJunctionPlanInfo(String id) {
        // 校验存在
        validateTslJunctionPlanInfoExists(id);
        // 删除
        tslJunctionPlanInfoMapper.deleteById(id);
    }

    private void validateTslJunctionPlanInfoExists(String id) {
        if (tslJunctionPlanInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_JUNCTION_PLAN_INFO_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslJunctionPlanInfoDO getTslJunctionPlanInfo(String id) {
        return tslJunctionPlanInfoMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslJunctionPlanInfoDO> getTslJunctionPlanInfoPage(TrafficTslJunctionPlanInfoPageReqVO pageReqVO) {
        return tslJunctionPlanInfoMapper.selectPage(pageReqVO);
    }

}