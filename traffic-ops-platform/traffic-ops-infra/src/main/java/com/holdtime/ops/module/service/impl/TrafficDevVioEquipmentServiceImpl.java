package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficDevVioEquipmentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDevVioEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficDevVioEquipmentMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 违法抓拍设备 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficDevVioEquipmentServiceImpl implements TrafficDevVioEquipmentService {

    @Resource
    private TrafficDevVioEquipmentMapper devVioEquipmentMapper;

    @Override
    public String createDevVioEquipment(TrafficDevVioEquipmentSaveReqVO createReqVO) {
        // 插入
        TrafficDevVioEquipmentDO devVioEquipment = BeanUtils.toBean(createReqVO, TrafficDevVioEquipmentDO.class);
        devVioEquipmentMapper.insert(devVioEquipment);
        // 返回
        return devVioEquipment.getId();
    }

    @Override
    public void updateDevVioEquipment(TrafficDevVioEquipmentSaveReqVO updateReqVO) {
        // 校验存在
        validateDevVioEquipmentExists(updateReqVO.getId());
        // 更新
        TrafficDevVioEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, TrafficDevVioEquipmentDO.class);
        devVioEquipmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteDevVioEquipment(String id) {
        // 校验存在
        validateDevVioEquipmentExists(id);
        // 删除
        devVioEquipmentMapper.deleteById(id);
    }

    private void validateDevVioEquipmentExists(String id) {
        if (devVioEquipmentMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.DEV_VIO_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficDevVioEquipmentDO getDevVioEquipment(String id) {
        return devVioEquipmentMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficDevVioEquipmentDO> getDevVioEquipmentPage(TrafficDevVioEquipmentPageReqVO pageReqVO) {
        return devVioEquipmentMapper.selectPage(pageReqVO);
    }

}