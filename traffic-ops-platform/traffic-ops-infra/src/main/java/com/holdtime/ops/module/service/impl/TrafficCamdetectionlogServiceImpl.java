package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficCamdetectionlogService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficCamdetectionlogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficCamdetectionlogMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备状态检测 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficCamdetectionlogServiceImpl implements TrafficCamdetectionlogService {

    @Resource
    private TrafficCamdetectionlogMapper camdetectionlogMapper;

    @Override
    public Long createCamdetectionlog(TrafficCamdetectionlogSaveReqVO createReqVO) {
        // 插入
        TrafficCamdetectionlogDO camdetectionlog = BeanUtils.toBean(createReqVO, TrafficCamdetectionlogDO.class);
        camdetectionlogMapper.insert(camdetectionlog);
        // 返回
        return camdetectionlog.getId();
    }

    @Override
    public void updateCamdetectionlog(TrafficCamdetectionlogSaveReqVO updateReqVO) {
        // 校验存在
        validateCamdetectionlogExists(updateReqVO.getId());
        // 更新
        TrafficCamdetectionlogDO updateObj = BeanUtils.toBean(updateReqVO, TrafficCamdetectionlogDO.class);
        camdetectionlogMapper.updateById(updateObj);
    }

    @Override
    public void deleteCamdetectionlog(Long id) {
        // 校验存在
        validateCamdetectionlogExists(id);
        // 删除
        camdetectionlogMapper.deleteById(id);
    }

    private void validateCamdetectionlogExists(Long id) {
        if (camdetectionlogMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.CAMDETECTIONLOG_NOT_EXISTS);
        }
    }

    @Override
    public TrafficCamdetectionlogDO getCamdetectionlog(Long id) {
        return camdetectionlogMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficCamdetectionlogDO> getCamdetectionlogPage(TrafficCamdetectionlogPageReqVO pageReqVO) {
        return camdetectionlogMapper.selectPage(pageReqVO);
    }

}