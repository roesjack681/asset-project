package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintainFlowRecordService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintainFlowRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintainFlowRecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保全流程 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsMaintainFlowRecordServiceImpl implements TrafficOpsMaintainFlowRecordService {

    @Resource
    private TrafficOpsMaintainFlowRecordMapper opsMaintainFlowRecordMapper;

    @Override
    public String createOpsMaintainFlowRecord(TrafficOpsMaintainFlowRecordSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintainFlowRecordDO opsMaintainFlowRecord = BeanUtils.toBean(createReqVO, TrafficOpsMaintainFlowRecordDO.class);
        opsMaintainFlowRecordMapper.insert(opsMaintainFlowRecord);
        // 返回
        return opsMaintainFlowRecord.getId();
    }

    @Override
    public void updateOpsMaintainFlowRecord(TrafficOpsMaintainFlowRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintainFlowRecordExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintainFlowRecordDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintainFlowRecordDO.class);
        opsMaintainFlowRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintainFlowRecord(String id) {
        // 校验存在
        validateOpsMaintainFlowRecordExists(id);
        // 删除
        opsMaintainFlowRecordMapper.deleteById(id);
    }

    private void validateOpsMaintainFlowRecordExists(String id) {
        if (opsMaintainFlowRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTAIN_FLOW_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintainFlowRecordDO getOpsMaintainFlowRecord(String id) {
        return opsMaintainFlowRecordMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintainFlowRecordDO> getOpsMaintainFlowRecordPage(TrafficOpsMaintainFlowRecordPageReqVO pageReqVO) {
        return opsMaintainFlowRecordMapper.selectPage(pageReqVO);
    }

}