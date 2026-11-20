package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsPoliceEquipmentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsPoliceEquipmentMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 警用装备资产信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsPoliceEquipmentServiceImpl implements TrafficOpsPoliceEquipmentService {

    @Resource
    private TrafficOpsPoliceEquipmentMapper opsPoliceEquipmentMapper;

    @Override
    public String createOpsPoliceEquipment(TrafficOpsPoliceEquipmentSaveReqVO createReqVO) {
        // 插入
        TrafficOpsPoliceEquipmentDO opsPoliceEquipment = BeanUtils.toBean(createReqVO, TrafficOpsPoliceEquipmentDO.class);
        opsPoliceEquipmentMapper.insert(opsPoliceEquipment);
        // 返回
        return opsPoliceEquipment.getId();
    }

    @Override
    public void updateOpsPoliceEquipment(TrafficOpsPoliceEquipmentSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsPoliceEquipmentExists(updateReqVO.getId());
        // 更新
        TrafficOpsPoliceEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsPoliceEquipmentDO.class);
        opsPoliceEquipmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsPoliceEquipment(String id) {
        // 校验存在
        validateOpsPoliceEquipmentExists(id);
        // 删除
        opsPoliceEquipmentMapper.deleteById(id);
    }

    private void validateOpsPoliceEquipmentExists(String id) {
        if (opsPoliceEquipmentMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsPoliceEquipmentDO getOpsPoliceEquipment(String id) {
        return opsPoliceEquipmentMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsPoliceEquipmentDO> getOpsPoliceEquipmentPage(TrafficOpsPoliceEquipmentPageReqVO pageReqVO) {
        return opsPoliceEquipmentMapper.selectPage(pageReqVO);
    }

}