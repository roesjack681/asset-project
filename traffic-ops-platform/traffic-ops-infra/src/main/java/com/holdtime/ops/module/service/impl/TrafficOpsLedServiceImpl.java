package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsLedService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsLedDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsLedMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * Led屏 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsLedServiceImpl implements TrafficOpsLedService {

    @Resource
    private TrafficOpsLedMapper opsLedMapper;

    @Override
    public String createOpsLed(TrafficOpsLedSaveReqVO createReqVO) {
        // 插入
        TrafficOpsLedDO opsLed = BeanUtils.toBean(createReqVO, TrafficOpsLedDO.class);
        opsLedMapper.insert(opsLed);
        // 返回
        return opsLed.getId();
    }

    @Override
    public void updateOpsLed(TrafficOpsLedSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsLedExists(updateReqVO.getId());
        // 更新
        TrafficOpsLedDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsLedDO.class);
        opsLedMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsLed(String id) {
        // 校验存在
        validateOpsLedExists(id);
        // 删除
        opsLedMapper.deleteById(id);
    }

    private void validateOpsLedExists(String id) {
        if (opsLedMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_LED_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsLedDO getOpsLed(String id) {
        return opsLedMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsLedDO> getOpsLedPage(TrafficOpsLedPageReqVO pageReqVO) {
        return opsLedMapper.selectPage(pageReqVO);
    }

}