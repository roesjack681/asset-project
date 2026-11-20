package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceUnfixableFollowService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceUnfixableFollowDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceUnfixableFollowMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保无法修复跟进 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsMaintenanceUnfixableFollowServiceImpl implements TrafficOpsMaintenanceUnfixableFollowService {

    @Resource
    private TrafficOpsMaintenanceUnfixableFollowMapper opsMaintenanceUnfixableFollowMapper;

    @Override
    public String createOpsMaintenanceUnfixableFollow(TrafficOpsMaintenanceUnfixableFollowSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceUnfixableFollowDO opsMaintenanceUnfixableFollow = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceUnfixableFollowDO.class);
        opsMaintenanceUnfixableFollowMapper.insert(opsMaintenanceUnfixableFollow);
        // 返回
        return opsMaintenanceUnfixableFollow.getId();
    }

    @Override
    public void updateOpsMaintenanceUnfixableFollow(TrafficOpsMaintenanceUnfixableFollowSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceUnfixableFollowExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceUnfixableFollowDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceUnfixableFollowDO.class);
        opsMaintenanceUnfixableFollowMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenanceUnfixableFollow(String id) {
        // 校验存在
        validateOpsMaintenanceUnfixableFollowExists(id);
        // 删除
        opsMaintenanceUnfixableFollowMapper.deleteById(id);
    }

    private void validateOpsMaintenanceUnfixableFollowExists(String id) {
        if (opsMaintenanceUnfixableFollowMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_UNFIXABLE_FOLLOW_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceUnfixableFollowDO getOpsMaintenanceUnfixableFollow(String id) {
        return opsMaintenanceUnfixableFollowMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceUnfixableFollowDO> getOpsMaintenanceUnfixableFollowPage(TrafficOpsMaintenanceUnfixableFollowPageReqVO pageReqVO) {
        return opsMaintenanceUnfixableFollowMapper.selectPage(pageReqVO);
    }

}