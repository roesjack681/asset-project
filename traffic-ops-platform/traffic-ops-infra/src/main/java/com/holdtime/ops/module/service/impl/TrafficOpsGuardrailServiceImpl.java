package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsGuardrailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsGuardrailServiceImpl implements TrafficOpsGuardrailService {

    @Resource
    private TrafficOpsGuardrailMapper opsGuardrailMapper;

    @Override
    public String createOpsGuardrail(TrafficOpsGuardrailSaveReqVO createReqVO) {
        // 插入
        TrafficOpsGuardrailDO opsGuardrail = BeanUtils.toBean(createReqVO, TrafficOpsGuardrailDO.class);
        opsGuardrailMapper.insert(opsGuardrail);
        // 返回
        return opsGuardrail.getId();
    }

    @Override
    public void updateOpsGuardrail(TrafficOpsGuardrailSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsGuardrailExists(updateReqVO.getId());
        // 更新
        TrafficOpsGuardrailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsGuardrailDO.class);
        opsGuardrailMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsGuardrail(String id) {
        // 校验存在
        validateOpsGuardrailExists(id);
        // 删除
        opsGuardrailMapper.deleteById(id);
    }

    private void validateOpsGuardrailExists(String id) {
        if (opsGuardrailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_GUARDRAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsGuardrailDO getOpsGuardrail(String id) {
        return opsGuardrailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsGuardrailDO> getOpsGuardrailPage(TrafficOpsGuardrailPageReqVO pageReqVO) {
        return opsGuardrailMapper.selectPage(pageReqVO);
    }

}