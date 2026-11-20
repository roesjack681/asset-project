package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceUnfixableInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceUnfixableInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceUnfixableInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保无法修复信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsMaintenanceUnfixableInfoServiceImpl implements TrafficOpsMaintenanceUnfixableInfoService {

    @Resource
    private TrafficOpsMaintenanceUnfixableInfoMapper opsMaintenanceUnfixableInfoMapper;

    @Override
    public String createOpsMaintenanceUnfixableInfo(TrafficOpsMaintenanceUnfixableInfoSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceUnfixableInfoDO opsMaintenanceUnfixableInfo = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceUnfixableInfoDO.class);
        opsMaintenanceUnfixableInfoMapper.insert(opsMaintenanceUnfixableInfo);
        // 返回
        return opsMaintenanceUnfixableInfo.getId();
    }

    @Override
    public void updateOpsMaintenanceUnfixableInfo(TrafficOpsMaintenanceUnfixableInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceUnfixableInfoExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceUnfixableInfoDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceUnfixableInfoDO.class);
        opsMaintenanceUnfixableInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenanceUnfixableInfo(String id) {
        // 校验存在
        validateOpsMaintenanceUnfixableInfoExists(id);
        // 删除
        opsMaintenanceUnfixableInfoMapper.deleteById(id);
    }

    private void validateOpsMaintenanceUnfixableInfoExists(String id) {
        if (opsMaintenanceUnfixableInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_UNFIXABLE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceUnfixableInfoDO getOpsMaintenanceUnfixableInfo(String id) {
        return opsMaintenanceUnfixableInfoMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceUnfixableInfoDO> getOpsMaintenanceUnfixableInfoPage(TrafficOpsMaintenanceUnfixableInfoPageReqVO pageReqVO) {
        return opsMaintenanceUnfixableInfoMapper.selectPage(pageReqVO);
    }

}