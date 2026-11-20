package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsSignalLightService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsSignalLightDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalLightMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信号灯 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsSignalLightServiceImpl implements TrafficOpsSignalLightService {

    @Resource
    private TrafficOpsSignalLightMapper opsSignalLightMapper;

    @Override
    public String createOpsSignalLight(TrafficOpsSignalLightSaveReqVO createReqVO) {
        // 插入
        TrafficOpsSignalLightDO opsSignalLight = BeanUtils.toBean(createReqVO, TrafficOpsSignalLightDO.class);
        opsSignalLightMapper.insert(opsSignalLight);
        // 返回
        return opsSignalLight.getId();
    }

    @Override
    public void updateOpsSignalLight(TrafficOpsSignalLightSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsSignalLightExists(updateReqVO.getId());
        // 更新
        TrafficOpsSignalLightDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsSignalLightDO.class);
        opsSignalLightMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsSignalLight(String id) {
        // 校验存在
        validateOpsSignalLightExists(id);
        // 删除
        opsSignalLightMapper.deleteById(id);
    }

    private void validateOpsSignalLightExists(String id) {
        if (opsSignalLightMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_SIGNAL_LIGHT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsSignalLightDO getOpsSignalLight(String id) {
        return opsSignalLightMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsSignalLightDO> getOpsSignalLightPage(TrafficOpsSignalLightPageReqVO pageReqVO) {
        return opsSignalLightMapper.selectPage(pageReqVO);
    }

}