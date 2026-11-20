package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficLightsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficLightsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficLightsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信号灯信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficLightsServiceImpl implements TrafficLightsService {

    @Resource
    private TrafficLightsMapper lightsMapper;

    @Override
    public Long createLights(TrafficLightsSaveReqVO createReqVO) {
        // 插入
        TrafficLightsDO lights = BeanUtils.toBean(createReqVO, TrafficLightsDO.class);
        lightsMapper.insert(lights);
        // 返回
        return lights.getId();
    }

    @Override
    public void updateLights(TrafficLightsSaveReqVO updateReqVO) {
        // 校验存在
        validateLightsExists(updateReqVO.getId());
        // 更新
        TrafficLightsDO updateObj = BeanUtils.toBean(updateReqVO, TrafficLightsDO.class);
        lightsMapper.updateById(updateObj);
    }

    @Override
    public void deleteLights(Long id) {
        // 校验存在
        validateLightsExists(id);
        // 删除
        lightsMapper.deleteById(id);
    }

    private void validateLightsExists(Long id) {
        if (lightsMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.LIGHTS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficLightsDO getLights(Long id) {
        return lightsMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficLightsDO> getLightsPage(TrafficLightsPageReqVO pageReqVO) {
        return lightsMapper.selectPage(pageReqVO);
    }

}