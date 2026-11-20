package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficDevTollgateRectificationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDevTollgateRectificationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficDevTollgateRectificationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 卡口质量评价 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficDevTollgateRectificationServiceImpl implements TrafficDevTollgateRectificationService {

    @Resource
    private TrafficDevTollgateRectificationMapper devTollgateRectificationMapper;

    @Override
    public String createDevTollgateRectification(TrafficDevTollgateRectificationSaveReqVO createReqVO) {
        // 插入
        TrafficDevTollgateRectificationDO devTollgateRectification = BeanUtils.toBean(createReqVO, TrafficDevTollgateRectificationDO.class);
        devTollgateRectificationMapper.insert(devTollgateRectification);
        // 返回
        return devTollgateRectification.getId();
    }

    @Override
    public void updateDevTollgateRectification(TrafficDevTollgateRectificationSaveReqVO updateReqVO) {
        // 校验存在
        validateDevTollgateRectificationExists(updateReqVO.getId());
        // 更新
        TrafficDevTollgateRectificationDO updateObj = BeanUtils.toBean(updateReqVO, TrafficDevTollgateRectificationDO.class);
        devTollgateRectificationMapper.updateById(updateObj);
    }

    @Override
    public void deleteDevTollgateRectification(String id) {
        // 校验存在
        validateDevTollgateRectificationExists(id);
        // 删除
        devTollgateRectificationMapper.deleteById(id);
    }

    private void validateDevTollgateRectificationExists(String id) {
        if (devTollgateRectificationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.DEV_TOLLGATE_RECTIFICATION_NOT_EXISTS);
        }
    }

    @Override
    public TrafficDevTollgateRectificationDO getDevTollgateRectification(String id) {
        return devTollgateRectificationMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficDevTollgateRectificationDO> getDevTollgateRectificationPage(TrafficDevTollgateRectificationPageReqVO pageReqVO) {
        return devTollgateRectificationMapper.selectPage(pageReqVO);
    }

}