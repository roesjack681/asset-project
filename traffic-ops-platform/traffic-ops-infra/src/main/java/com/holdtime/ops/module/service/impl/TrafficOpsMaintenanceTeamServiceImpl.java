package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceTeamService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceTeamDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceTeamMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保组员信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMaintenanceTeamServiceImpl implements TrafficOpsMaintenanceTeamService {

    @Resource
    private TrafficOpsMaintenanceTeamMapper opsMaintenanceTeamMapper;

    @Override
    public String createOpsMaintenanceTeam(TrafficOpsMaintenanceTeamSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceTeamDO opsMaintenanceTeam = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceTeamDO.class);
        opsMaintenanceTeamMapper.insert(opsMaintenanceTeam);
        // 返回
        return opsMaintenanceTeam.getId();
    }

    @Override
    public void updateOpsMaintenanceTeam(TrafficOpsMaintenanceTeamSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceTeamExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceTeamDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceTeamDO.class);
        opsMaintenanceTeamMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenanceTeam(String id) {
        // 校验存在
        validateOpsMaintenanceTeamExists(id);
        // 删除
        opsMaintenanceTeamMapper.deleteById(id);
    }

    private void validateOpsMaintenanceTeamExists(String id) {
        if (opsMaintenanceTeamMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_TEAM_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceTeamDO getOpsMaintenanceTeam(String id) {
        return opsMaintenanceTeamMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceTeamDO> getOpsMaintenanceTeamPage(TrafficOpsMaintenanceTeamPageReqVO pageReqVO) {
        return opsMaintenanceTeamMapper.selectPage(pageReqVO);
    }

}