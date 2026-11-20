package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMaintenanceServiceImpl implements TrafficOpsMaintenanceService {

    @Resource
    private TrafficOpsMaintenanceMapper opsMaintenanceMapper;

    @Override
    public String createOpsMaintenance(TrafficOpsMaintenanceSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceDO opsMaintenance = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceDO.class);
        opsMaintenanceMapper.insert(opsMaintenance);
        // 返回
        return opsMaintenance.getId();
    }

    @Override
    public void updateOpsMaintenance(TrafficOpsMaintenanceSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceDO.class);
        opsMaintenanceMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenance(String id) {
        // 校验存在
        validateOpsMaintenanceExists(id);
        // 删除
        opsMaintenanceMapper.deleteById(id);
    }

    private void validateOpsMaintenanceExists(String id) {
        if (opsMaintenanceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceDO getOpsMaintenance(String id) {
        return opsMaintenanceMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceDO> getOpsMaintenancePage(String isPagination, TrafficOpsMaintenancePageReqVO pageReqVO) {
        if (!OpsConstants.STRING_ONE.equals(isPagination)) {
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        return opsMaintenanceMapper.selectPage(pageReqVO);
    }

}