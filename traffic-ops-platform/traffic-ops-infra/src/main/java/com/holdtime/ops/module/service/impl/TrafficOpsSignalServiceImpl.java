package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsSignalService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信号灯 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsSignalServiceImpl implements TrafficOpsSignalService {

    @Resource
    private TrafficOpsSignalMapper opsSignalMapper;

    @Override
    public String createOpsSignal(TrafficOpsSignalSaveReqVO createReqVO) {
        // 插入
        TrafficOpsSignalDO opsSignal = BeanUtils.toBean(createReqVO, TrafficOpsSignalDO.class);
        opsSignalMapper.insert(opsSignal);
        // 返回
        return opsSignal.getId();
    }

    @Override
    public void updateOpsSignal(TrafficOpsSignalSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsSignalExists(updateReqVO.getId());
        // 更新
        TrafficOpsSignalDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsSignalDO.class);
        opsSignalMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsSignal(String id) {
        // 校验存在
        validateOpsSignalExists(id);
        // 删除
        opsSignalMapper.deleteById(id);
    }

    private void validateOpsSignalExists(String id) {
        if (opsSignalMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_SIGNAL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsSignalDO getOpsSignal(String id) {
        return opsSignalMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsSignalDO> getOpsSignalPage(TrafficOpsSignalPageReqVO pageReqVO) {
        return opsSignalMapper.selectPage(pageReqVO);
    }

}