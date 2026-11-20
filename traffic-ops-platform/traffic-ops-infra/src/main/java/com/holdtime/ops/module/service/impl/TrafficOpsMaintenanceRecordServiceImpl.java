package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceRecordService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceRecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 维保记录 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficOpsMaintenanceRecordServiceImpl implements TrafficOpsMaintenanceRecordService {

    @Resource
    private TrafficOpsMaintenanceRecordMapper opsMaintenanceRecordMapper;

    @Override
    public String createOpsMaintenanceRecord(TrafficOpsMaintenanceRecordSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceRecordDO opsMaintenanceRecord = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceRecordDO.class);
        opsMaintenanceRecordMapper.insert(opsMaintenanceRecord);
        // 返回
        return opsMaintenanceRecord.getId();
    }

    @Override
    public void updateOpsMaintenanceRecord(TrafficOpsMaintenanceRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceRecordExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceRecordDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceRecordDO.class);
        opsMaintenanceRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenanceRecord(String id) {
        // 校验存在
        validateOpsMaintenanceRecordExists(id);
        // 删除
        opsMaintenanceRecordMapper.deleteById(id);
    }

    private void validateOpsMaintenanceRecordExists(String id) {
        if (opsMaintenanceRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceRecordDO getOpsMaintenanceRecord(String id) {
        return opsMaintenanceRecordMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceRecordDO> getOpsMaintenanceRecordPage(TrafficOpsMaintenanceRecordPageReqVO pageReqVO) {
        return opsMaintenanceRecordMapper.selectPage(pageReqVO);
    }

}