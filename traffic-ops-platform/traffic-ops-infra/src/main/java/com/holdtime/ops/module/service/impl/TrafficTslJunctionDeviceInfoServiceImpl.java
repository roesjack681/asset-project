package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslJunctionDeviceInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionDeviceInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionDeviceInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控交叉口设备信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslJunctionDeviceInfoServiceImpl implements TrafficTslJunctionDeviceInfoService {

    @Resource
    private TrafficTslJunctionDeviceInfoMapper tslJunctionDeviceInfoMapper;

    @Override
    public String createTslJunctionDeviceInfo(TrafficTslJunctionDeviceInfoSaveReqVO createReqVO) {
        // 插入
        TrafficTslJunctionDeviceInfoDO tslJunctionDeviceInfo = BeanUtils.toBean(createReqVO, TrafficTslJunctionDeviceInfoDO.class);
        tslJunctionDeviceInfoMapper.insert(tslJunctionDeviceInfo);
        // 返回
        return tslJunctionDeviceInfo.getId();
    }

    @Override
    public void updateTslJunctionDeviceInfo(TrafficTslJunctionDeviceInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateTslJunctionDeviceInfoExists(updateReqVO.getId());
        // 更新
        TrafficTslJunctionDeviceInfoDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslJunctionDeviceInfoDO.class);
        tslJunctionDeviceInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslJunctionDeviceInfo(String id) {
        // 校验存在
        validateTslJunctionDeviceInfoExists(id);
        // 删除
        tslJunctionDeviceInfoMapper.deleteById(id);
    }

    private void validateTslJunctionDeviceInfoExists(String id) {
        if (tslJunctionDeviceInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_JUNCTION_DEVICE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslJunctionDeviceInfoDO getTslJunctionDeviceInfo(String id) {
        return tslJunctionDeviceInfoMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslJunctionDeviceInfoDO> getTslJunctionDeviceInfoPage(TrafficTslJunctionDeviceInfoPageReqVO pageReqVO) {
        return tslJunctionDeviceInfoMapper.selectPage(pageReqVO);
    }

}