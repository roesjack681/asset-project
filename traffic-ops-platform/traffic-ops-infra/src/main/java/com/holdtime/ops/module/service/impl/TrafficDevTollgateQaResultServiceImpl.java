package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficDevTollgateQaResultService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDevTollgateQaResultDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficDevTollgateQaResultMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 卡口质量检测结果 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficDevTollgateQaResultServiceImpl implements TrafficDevTollgateQaResultService {

    @Resource
    private TrafficDevTollgateQaResultMapper devTollgateQaResultMapper;

    @Override
    public String createDevTollgateQaResult(TrafficDevTollgateQaResultSaveReqVO createReqVO) {
        // 插入
        TrafficDevTollgateQaResultDO devTollgateQaResult = BeanUtils.toBean(createReqVO, TrafficDevTollgateQaResultDO.class);
        devTollgateQaResultMapper.insert(devTollgateQaResult);
        // 返回
        return devTollgateQaResult.getId();
    }

    @Override
    public void updateDevTollgateQaResult(TrafficDevTollgateQaResultSaveReqVO updateReqVO) {
        // 校验存在
        validateDevTollgateQaResultExists(updateReqVO.getId());
        // 更新
        TrafficDevTollgateQaResultDO updateObj = BeanUtils.toBean(updateReqVO, TrafficDevTollgateQaResultDO.class);
        devTollgateQaResultMapper.updateById(updateObj);
    }

    @Override
    public void deleteDevTollgateQaResult(String id) {
        // 校验存在
        validateDevTollgateQaResultExists(id);
        // 删除
        devTollgateQaResultMapper.deleteById(id);
    }

    private void validateDevTollgateQaResultExists(String id) {
        if (devTollgateQaResultMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.DEV_TOLLGATE_QA_RESULT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficDevTollgateQaResultDO getDevTollgateQaResult(String id) {
        return devTollgateQaResultMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficDevTollgateQaResultDO> getDevTollgateQaResultPage(TrafficDevTollgateQaResultPageReqVO pageReqVO) {
        return devTollgateQaResultMapper.selectPage(pageReqVO);
    }

}