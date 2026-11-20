package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsGuardrailGapService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailGapDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailGapMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏开口信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsGuardrailGapServiceImpl implements TrafficOpsGuardrailGapService {

    @Resource
    private TrafficOpsGuardrailGapMapper opsGuardrailGapMapper;

    @Override
    public String createOpsGuardrailGap(TrafficOpsGuardrailGapSaveReqVO createReqVO) {
        // 插入
        TrafficOpsGuardrailGapDO opsGuardrailGap = BeanUtils.toBean(createReqVO, TrafficOpsGuardrailGapDO.class);
        opsGuardrailGapMapper.insert(opsGuardrailGap);
        // 返回
        return opsGuardrailGap.getId();
    }

    @Override
    public void updateOpsGuardrailGap(TrafficOpsGuardrailGapSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsGuardrailGapExists(updateReqVO.getId());
        // 更新
        TrafficOpsGuardrailGapDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsGuardrailGapDO.class);
        opsGuardrailGapMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsGuardrailGap(String id) {
        // 校验存在
        validateOpsGuardrailGapExists(id);
        // 删除
        opsGuardrailGapMapper.deleteById(id);
    }

    private void validateOpsGuardrailGapExists(String id) {
        if (opsGuardrailGapMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_GUARDRAIL_GAP_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsGuardrailGapDO getOpsGuardrailGap(String id) {
        return opsGuardrailGapMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsGuardrailGapDO> getOpsGuardrailGapPage(TrafficOpsGuardrailGapPageReqVO pageReqVO) {
        return opsGuardrailGapMapper.selectPage(pageReqVO);
    }

}