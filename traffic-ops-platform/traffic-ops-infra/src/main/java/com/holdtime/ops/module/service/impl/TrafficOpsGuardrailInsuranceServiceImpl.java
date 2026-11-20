package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsGuardrailInsuranceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailInsuranceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailInsuranceMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏保险信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsGuardrailInsuranceServiceImpl implements TrafficOpsGuardrailInsuranceService {

    @Resource
    private TrafficOpsGuardrailInsuranceMapper opsGuardrailInsuranceMapper;

    @Override
    public String createOpsGuardrailInsurance(TrafficOpsGuardrailInsuranceSaveReqVO createReqVO) {
        // 插入
        TrafficOpsGuardrailInsuranceDO opsGuardrailInsurance = BeanUtils.toBean(createReqVO, TrafficOpsGuardrailInsuranceDO.class);
        opsGuardrailInsuranceMapper.insert(opsGuardrailInsurance);
        // 返回
        return opsGuardrailInsurance.getId();
    }

    @Override
    public void updateOpsGuardrailInsurance(TrafficOpsGuardrailInsuranceSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsGuardrailInsuranceExists(updateReqVO.getId());
        // 更新
        TrafficOpsGuardrailInsuranceDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsGuardrailInsuranceDO.class);
        opsGuardrailInsuranceMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsGuardrailInsurance(String id) {
        // 校验存在
        validateOpsGuardrailInsuranceExists(id);
        // 删除
        opsGuardrailInsuranceMapper.deleteById(id);
    }

    private void validateOpsGuardrailInsuranceExists(String id) {
        if (opsGuardrailInsuranceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_GUARDRAIL_INSURANCE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsGuardrailInsuranceDO getOpsGuardrailInsurance(String id) {
        return opsGuardrailInsuranceMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsGuardrailInsuranceDO> getOpsGuardrailInsurancePage(TrafficOpsGuardrailInsurancePageReqVO pageReqVO) {
        return opsGuardrailInsuranceMapper.selectPage(pageReqVO);
    }

}