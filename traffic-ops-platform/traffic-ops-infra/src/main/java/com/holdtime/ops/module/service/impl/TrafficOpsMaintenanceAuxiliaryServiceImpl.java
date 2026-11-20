package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceAuxiliaryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceAuxiliaryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceAuxiliaryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏辅助设备维护详细 Service 实现类
 *
 * @author yanxl
 */
@Service
@Validated
public class TrafficOpsMaintenanceAuxiliaryServiceImpl implements TrafficOpsMaintenanceAuxiliaryService {

    @Resource
    private TrafficOpsMaintenanceAuxiliaryMapper opsMaintenanceAuxiliaryMapper;

    @Override
    public String createOpsMaintenanceAuxiliary(TrafficOpsMaintenanceAuxiliarySaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceAuxiliaryDO opsMaintenanceAuxiliary = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceAuxiliaryDO.class);
        opsMaintenanceAuxiliaryMapper.insert(opsMaintenanceAuxiliary);
        // 返回
        return opsMaintenanceAuxiliary.getId();
    }

    @Override
    public void updateOpsMaintenanceAuxiliary(TrafficOpsMaintenanceAuxiliarySaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceAuxiliaryExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceAuxiliaryDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceAuxiliaryDO.class);
        opsMaintenanceAuxiliaryMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenanceAuxiliary(String id) {
        // 校验存在
        validateOpsMaintenanceAuxiliaryExists(id);
        // 删除
        opsMaintenanceAuxiliaryMapper.deleteById(id);
    }

    private void validateOpsMaintenanceAuxiliaryExists(String id) {
        if (opsMaintenanceAuxiliaryMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_AUXILIARY_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceAuxiliaryDO getOpsMaintenanceAuxiliary(String id) {
        return opsMaintenanceAuxiliaryMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceAuxiliaryDO> getOpsMaintenanceAuxiliaryPage(TrafficOpsMaintenanceAuxiliaryPageReqVO pageReqVO) {
        return opsMaintenanceAuxiliaryMapper.selectPage(pageReqVO);
    }

}