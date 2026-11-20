package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficVioSurveilScreenService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficVioSurveilScreenMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 违法抓拍有效记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficVioSurveilScreenServiceImpl implements TrafficVioSurveilScreenService {

    @Resource
    private TrafficVioSurveilScreenMapper vioSurveilScreenMapper;

    @Override
    public String createVioSurveilScreen(TrafficVioSurveilScreenSaveReqVO createReqVO) {
        // 插入
        TrafficVioSurveilScreenDO vioSurveilScreen = BeanUtils.toBean(createReqVO, TrafficVioSurveilScreenDO.class);
        vioSurveilScreenMapper.insert(vioSurveilScreen);
        // 返回
        return vioSurveilScreen.getId();
    }

    @Override
    public void updateVioSurveilScreen(TrafficVioSurveilScreenSaveReqVO updateReqVO) {
        // 校验存在
        validateVioSurveilScreenExists(updateReqVO.getId());
        // 更新
        TrafficVioSurveilScreenDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioSurveilScreenDO.class);
        vioSurveilScreenMapper.updateById(updateObj);
    }

    @Override
    public void deleteVioSurveilScreen(String id) {
        // 校验存在
        validateVioSurveilScreenExists(id);
        // 删除
        vioSurveilScreenMapper.deleteById(id);
    }

    private void validateVioSurveilScreenExists(String id) {
        if (vioSurveilScreenMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_SURVEIL_SCREEN_NOT_EXISTS);
        }
    }

    @Override
    public TrafficVioSurveilScreenDO getVioSurveilScreen(String id) {
        return vioSurveilScreenMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioSurveilScreenDO> getVioSurveilScreenPage(TrafficVioSurveilScreenPageReqVO pageReqVO) {
        return vioSurveilScreenMapper.selectPage(pageReqVO);
    }

}