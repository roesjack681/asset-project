package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficLightsdetectionlogService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficLightsdetectionlogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficLightsdetectionlogMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信号灯检测信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficLightsdetectionlogServiceImpl implements TrafficLightsdetectionlogService {

    @Resource
    private TrafficLightsdetectionlogMapper lightsdetectionlogMapper;

    @Override
    public Long createLightsdetectionlog(TrafficLightsdetectionlogSaveReqVO createReqVO) {
        // 插入
        TrafficLightsdetectionlogDO lightsdetectionlog = BeanUtils.toBean(createReqVO, TrafficLightsdetectionlogDO.class);
        lightsdetectionlogMapper.insert(lightsdetectionlog);
        // 返回
        return lightsdetectionlog.getId();
    }

    @Override
    public void updateLightsdetectionlog(TrafficLightsdetectionlogSaveReqVO updateReqVO) {
        // 校验存在
        validateLightsdetectionlogExists(updateReqVO.getId());
        // 更新
        TrafficLightsdetectionlogDO updateObj = BeanUtils.toBean(updateReqVO, TrafficLightsdetectionlogDO.class);
        lightsdetectionlogMapper.updateById(updateObj);
    }

    @Override
    public void deleteLightsdetectionlog(Long id) {
        // 校验存在
        validateLightsdetectionlogExists(id);
        // 删除
        lightsdetectionlogMapper.deleteById(id);
    }

    private void validateLightsdetectionlogExists(Long id) {
        if (lightsdetectionlogMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.LIGHTSDETECTIONLOG_NOT_EXISTS);
        }
    }

    @Override
    public TrafficLightsdetectionlogDO getLightsdetectionlog(Long id) {
        return lightsdetectionlogMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficLightsdetectionlogDO> getLightsdetectionlogPage(TrafficLightsdetectionlogPageReqVO pageReqVO) {
        return lightsdetectionlogMapper.selectPage(pageReqVO);
    }

}