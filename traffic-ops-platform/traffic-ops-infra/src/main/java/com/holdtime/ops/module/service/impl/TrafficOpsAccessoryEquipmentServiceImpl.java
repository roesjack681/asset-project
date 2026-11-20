package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsAccessoryEquipmentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsAccessoryEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsAccessoryEquipmentMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 杆件附属设备 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsAccessoryEquipmentServiceImpl implements TrafficOpsAccessoryEquipmentService {

    @Resource
    private TrafficOpsAccessoryEquipmentMapper opsAccessoryEquipmentMapper;

    @Override
    public String createOpsAccessoryEquipment(TrafficOpsAccessoryEquipmentSaveReqVO createReqVO) {
        // 插入
        TrafficOpsAccessoryEquipmentDO opsAccessoryEquipment = BeanUtils.toBean(createReqVO, TrafficOpsAccessoryEquipmentDO.class);
        opsAccessoryEquipmentMapper.insert(opsAccessoryEquipment);
        // 返回
        return opsAccessoryEquipment.getId();
    }

    @Override
    public void updateOpsAccessoryEquipment(TrafficOpsAccessoryEquipmentSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsAccessoryEquipmentExists(updateReqVO.getId());
        // 更新
        TrafficOpsAccessoryEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsAccessoryEquipmentDO.class);
        opsAccessoryEquipmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsAccessoryEquipment(String id) {
        // 校验存在
        validateOpsAccessoryEquipmentExists(id);
        // 删除
        opsAccessoryEquipmentMapper.deleteById(id);
    }

    private void validateOpsAccessoryEquipmentExists(String id) {
        if (opsAccessoryEquipmentMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ACCESSORY_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsAccessoryEquipmentDO getOpsAccessoryEquipment(String id) {
        return opsAccessoryEquipmentMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsAccessoryEquipmentDO> getOpsAccessoryEquipmentPage(TrafficOpsAccessoryEquipmentPageReqVO pageReqVO) {
        return opsAccessoryEquipmentMapper.selectPage(pageReqVO);
    }

}