package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficVioSurveilService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioSurveilDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficVioSurveilMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 违法抓拍待审记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficVioSurveilServiceImpl implements TrafficVioSurveilService {

    @Resource
    private TrafficVioSurveilMapper vioSurveilMapper;

    @Override
    public String createVioSurveil(TrafficVioSurveilSaveReqVO createReqVO) {
        // 插入
        TrafficVioSurveilDO vioSurveil = BeanUtils.toBean(createReqVO, TrafficVioSurveilDO.class);
        vioSurveilMapper.insert(vioSurveil);
        // 返回
        return vioSurveil.getId();
    }

    @Override
    public void updateVioSurveil(TrafficVioSurveilSaveReqVO updateReqVO) {
        // 校验存在
        validateVioSurveilExists(updateReqVO.getId());
        // 更新
        TrafficVioSurveilDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioSurveilDO.class);
        vioSurveilMapper.updateById(updateObj);
    }

    @Override
    public void deleteVioSurveil(String id) {
        // 校验存在
        validateVioSurveilExists(id);
        // 删除
        vioSurveilMapper.deleteById(id);
    }

    private void validateVioSurveilExists(String id) {
        if (vioSurveilMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_SURVEIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficVioSurveilDO getVioSurveil(String id) {
        return vioSurveilMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioSurveilDO> getVioSurveilPage(TrafficVioSurveilPageReqVO pageReqVO) {
        return vioSurveilMapper.selectPage(pageReqVO);
    }

}