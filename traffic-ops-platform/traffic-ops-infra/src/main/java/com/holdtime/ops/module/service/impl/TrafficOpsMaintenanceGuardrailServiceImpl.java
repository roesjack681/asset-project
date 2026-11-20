package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceGuardrailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceGuardrailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceGuardrailMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏维护详细 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsMaintenanceGuardrailServiceImpl implements TrafficOpsMaintenanceGuardrailService {

    @Resource
    private TrafficOpsMaintenanceGuardrailMapper opsMaintenanceGuardrailMapper;

    @Override
    public String createOpsMaintenanceGuardrail(TrafficOpsMaintenanceGuardrailSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceGuardrailDO opsMaintenanceGuardrail = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceGuardrailDO.class);
        opsMaintenanceGuardrailMapper.insert(opsMaintenanceGuardrail);
        // 返回
        return opsMaintenanceGuardrail.getId();
    }

    @Override
    public void updateOpsMaintenanceGuardrail(TrafficOpsMaintenanceGuardrailSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceGuardrailExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceGuardrailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceGuardrailDO.class);
        opsMaintenanceGuardrailMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenanceGuardrail(String id) {
        // 校验存在
        validateOpsMaintenanceGuardrailExists(id);
        // 删除
        opsMaintenanceGuardrailMapper.deleteById(id);
    }

    private void validateOpsMaintenanceGuardrailExists(String id) {
        if (opsMaintenanceGuardrailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_GUARDRAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceGuardrailDO getOpsMaintenanceGuardrail(String id) {
        return opsMaintenanceGuardrailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceGuardrailDO> getOpsMaintenanceGuardrailPage(TrafficOpsMaintenanceGuardrailPageReqVO pageReqVO) {
        return opsMaintenanceGuardrailMapper.selectPage(pageReqVO);
    }

}