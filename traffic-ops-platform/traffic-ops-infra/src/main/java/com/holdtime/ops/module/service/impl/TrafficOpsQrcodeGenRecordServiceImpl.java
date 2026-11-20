package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsQrcodeGenRecordService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeGenRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeGenRecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 资产二维码生成记录 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficOpsQrcodeGenRecordServiceImpl implements TrafficOpsQrcodeGenRecordService {

    @Resource
    private TrafficOpsQrcodeGenRecordMapper opsQrcodeGenRecordMapper;

    @Override
    public String createOpsQrcodeGenRecord(TrafficOpsQrcodeGenRecordSaveReqVO createReqVO) {
        // 插入
        TrafficOpsQrcodeGenRecordDO opsQrcodeGenRecord = BeanUtils.toBean(createReqVO, TrafficOpsQrcodeGenRecordDO.class);
        opsQrcodeGenRecordMapper.insert(opsQrcodeGenRecord);
        // 返回
        return opsQrcodeGenRecord.getId();
    }

    @Override
    public void updateOpsQrcodeGenRecord(TrafficOpsQrcodeGenRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsQrcodeGenRecordExists(updateReqVO.getId());
        // 更新
        TrafficOpsQrcodeGenRecordDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsQrcodeGenRecordDO.class);
        opsQrcodeGenRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsQrcodeGenRecord(String id) {
        // 校验存在
        validateOpsQrcodeGenRecordExists(id);
        // 删除
        opsQrcodeGenRecordMapper.deleteById(id);
    }

    private void validateOpsQrcodeGenRecordExists(String id) {
        if (opsQrcodeGenRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_QRCODE_GEN_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsQrcodeGenRecordDO getOpsQrcodeGenRecord(String id) {
        return opsQrcodeGenRecordMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsQrcodeGenRecordDO> getOpsQrcodeGenRecordPage(TrafficOpsQrcodeGenRecordPageReqVO pageReqVO) {
        return opsQrcodeGenRecordMapper.selectPage(pageReqVO);
    }

}