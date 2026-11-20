package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficCrossingService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficCrossingDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficCrossingMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 路口信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficCrossingServiceImpl implements TrafficCrossingService {

    @Resource
    private TrafficCrossingMapper crossingMapper;

    @Override
    public Long createCrossing(TrafficCrossingSaveReqVO createReqVO) {
        // 插入
        TrafficCrossingDO crossing = BeanUtils.toBean(createReqVO, TrafficCrossingDO.class);
        crossingMapper.insert(crossing);
        // 返回
        return crossing.getId();
    }

    @Override
    public void updateCrossing(TrafficCrossingSaveReqVO updateReqVO) {
        // 校验存在
        validateCrossingExists(updateReqVO.getId());
        // 更新
        TrafficCrossingDO updateObj = BeanUtils.toBean(updateReqVO, TrafficCrossingDO.class);
        crossingMapper.updateById(updateObj);
    }

    @Override
    public void deleteCrossing(Long id) {
        // 校验存在
        validateCrossingExists(id);
        // 删除
        crossingMapper.deleteById(id);
    }

    private void validateCrossingExists(Long id) {
        if (crossingMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.CROSSING_NOT_EXISTS);
        }
    }

    @Override
    public TrafficCrossingDO getCrossing(Long id) {
        return crossingMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficCrossingDO> getCrossingPage(TrafficCrossingPageReqVO pageReqVO) {
        return crossingMapper.selectPage(pageReqVO);
    }

}