package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficVioSurveilScreenErrService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenErrDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficVioSurveilScreenErrMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 违法抓拍无效记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficVioSurveilScreenErrServiceImpl implements TrafficVioSurveilScreenErrService {

    @Resource
    private TrafficVioSurveilScreenErrMapper vioSurveilScreenErrMapper;

    @Override
    public String createVioSurveilScreenErr(TrafficVioSurveilScreenErrSaveReqVO createReqVO) {
        // 插入
        TrafficVioSurveilScreenErrDO vioSurveilScreenErr = BeanUtils.toBean(createReqVO, TrafficVioSurveilScreenErrDO.class);
        vioSurveilScreenErrMapper.insert(vioSurveilScreenErr);
        // 返回
        return vioSurveilScreenErr.getId();
    }

    @Override
    public void updateVioSurveilScreenErr(TrafficVioSurveilScreenErrSaveReqVO updateReqVO) {
        // 校验存在
        validateVioSurveilScreenErrExists(updateReqVO.getId());
        // 更新
        TrafficVioSurveilScreenErrDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioSurveilScreenErrDO.class);
        vioSurveilScreenErrMapper.updateById(updateObj);
    }

    @Override
    public void deleteVioSurveilScreenErr(String id) {
        // 校验存在
        validateVioSurveilScreenErrExists(id);
        // 删除
        vioSurveilScreenErrMapper.deleteById(id);
    }

    private void validateVioSurveilScreenErrExists(String id) {
        if (vioSurveilScreenErrMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_SURVEIL_SCREEN_ERR_NOT_EXISTS);
        }
    }

    @Override
    public TrafficVioSurveilScreenErrDO getVioSurveilScreenErr(String id) {
        return vioSurveilScreenErrMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioSurveilScreenErrDO> getVioSurveilScreenErrPage(TrafficVioSurveilScreenErrPageReqVO pageReqVO) {
        return vioSurveilScreenErrMapper.selectPage(pageReqVO);
    }

}