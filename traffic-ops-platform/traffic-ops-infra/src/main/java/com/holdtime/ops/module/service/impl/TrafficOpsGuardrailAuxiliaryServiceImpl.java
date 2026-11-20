package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsGuardrailAuxiliaryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailAuxiliaryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailAuxiliaryMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏辅助设备 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsGuardrailAuxiliaryServiceImpl implements TrafficOpsGuardrailAuxiliaryService {

    @Resource
    private TrafficOpsGuardrailAuxiliaryMapper opsGuardrailAuxiliaryMapper;

    @Override
    public String createOpsGuardrailAuxiliary(TrafficOpsGuardrailAuxiliarySaveReqVO createReqVO) {
        // 插入
        TrafficOpsGuardrailAuxiliaryDO opsGuardrailAuxiliary = BeanUtils.toBean(createReqVO, TrafficOpsGuardrailAuxiliaryDO.class);
        opsGuardrailAuxiliaryMapper.insert(opsGuardrailAuxiliary);
        // 返回
        return opsGuardrailAuxiliary.getId();
    }

    @Override
    public void updateOpsGuardrailAuxiliary(TrafficOpsGuardrailAuxiliarySaveReqVO updateReqVO) {
        // 校验存在
        validateOpsGuardrailAuxiliaryExists(updateReqVO.getId());
        // 更新
        TrafficOpsGuardrailAuxiliaryDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsGuardrailAuxiliaryDO.class);
        opsGuardrailAuxiliaryMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsGuardrailAuxiliary(String id) {
        // 校验存在
        validateOpsGuardrailAuxiliaryExists(id);
        // 删除
        opsGuardrailAuxiliaryMapper.deleteById(id);
    }

    private void validateOpsGuardrailAuxiliaryExists(String id) {
        if (opsGuardrailAuxiliaryMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_GUARDRAIL_AUXILIARY_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsGuardrailAuxiliaryDO getOpsGuardrailAuxiliary(String id) {
        return opsGuardrailAuxiliaryMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsGuardrailAuxiliaryDO> getOpsGuardrailAuxiliaryPage(TrafficOpsGuardrailAuxiliaryPageReqVO pageReqVO) {
        return opsGuardrailAuxiliaryMapper.selectPage(pageReqVO);
    }

}