package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsPoliceMaintenanceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceMaintenanceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsPoliceMaintenanceMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 警用装备报修信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsPoliceMaintenanceServiceImpl implements TrafficOpsPoliceMaintenanceService {

    @Resource
    private TrafficOpsPoliceMaintenanceMapper opsPoliceMaintenanceMapper;

    @Override
    public String createOpsPoliceMaintenance(TrafficOpsPoliceMaintenanceSaveReqVO createReqVO) {
        // 插入
        TrafficOpsPoliceMaintenanceDO opsPoliceMaintenance = BeanUtils.toBean(createReqVO, TrafficOpsPoliceMaintenanceDO.class);
        opsPoliceMaintenanceMapper.insert(opsPoliceMaintenance);
        // 返回
        return opsPoliceMaintenance.getId();
    }

    @Override
    public void updateOpsPoliceMaintenance(TrafficOpsPoliceMaintenanceSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsPoliceMaintenanceExists(updateReqVO.getId());
        // 更新
        TrafficOpsPoliceMaintenanceDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsPoliceMaintenanceDO.class);
        opsPoliceMaintenanceMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsPoliceMaintenance(String id) {
        // 校验存在
        validateOpsPoliceMaintenanceExists(id);
        // 删除
        opsPoliceMaintenanceMapper.deleteById(id);
    }

    private void validateOpsPoliceMaintenanceExists(String id) {
        if (opsPoliceMaintenanceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_MAINTENANCE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsPoliceMaintenanceDO getOpsPoliceMaintenance(String id) {
        return opsPoliceMaintenanceMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsPoliceMaintenanceDO> getOpsPoliceMaintenancePage(TrafficOpsPoliceMaintenancePageReqVO pageReqVO) {
        return opsPoliceMaintenanceMapper.selectPage(pageReqVO);
    }

}