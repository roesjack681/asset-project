package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewOpsMaintenanceWorkStatusService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMaintenanceWorkStatusDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewOpsMaintenanceWorkStatusMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * VIEW Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ViewOpsMaintenanceWorkStatusServiceImpl implements ViewOpsMaintenanceWorkStatusService {

    @Resource
    private ViewOpsMaintenanceWorkStatusMapper viewOpsMaintenanceWorkStatusMapper;

    @Override
    public String createViewOpsMaintenanceWorkStatus(ViewOpsMaintenanceWorkStatusSaveReqVO createReqVO) {
        // 插入
        ViewOpsMaintenanceWorkStatusDO viewOpsMaintenanceWorkStatus = BeanUtils.toBean(createReqVO, ViewOpsMaintenanceWorkStatusDO.class);
        viewOpsMaintenanceWorkStatusMapper.insert(viewOpsMaintenanceWorkStatus);
        // 返回
        return viewOpsMaintenanceWorkStatus.getId();
    }

    @Override
    public void updateViewOpsMaintenanceWorkStatus(ViewOpsMaintenanceWorkStatusSaveReqVO updateReqVO) {
        // 校验存在
        validateViewOpsMaintenanceWorkStatusExists(updateReqVO.getId());
        // 更新
        ViewOpsMaintenanceWorkStatusDO updateObj = BeanUtils.toBean(updateReqVO, ViewOpsMaintenanceWorkStatusDO.class);
        viewOpsMaintenanceWorkStatusMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewOpsMaintenanceWorkStatus(String id) {
        // 校验存在
        validateViewOpsMaintenanceWorkStatusExists(id);
        // 删除
        viewOpsMaintenanceWorkStatusMapper.deleteById(id);
    }

    private void validateViewOpsMaintenanceWorkStatusExists(String id) {
        if (viewOpsMaintenanceWorkStatusMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIEW_OPS_MAINTENANCE_WORK_STATUS_NOT_EXISTS);
        }
    }

    @Override
    public ViewOpsMaintenanceWorkStatusDO getViewOpsMaintenanceWorkStatus(String id) {
        return viewOpsMaintenanceWorkStatusMapper.selectById(id);
    }

    @Override
    public PageResult<ViewOpsMaintenanceWorkStatusDO> getViewOpsMaintenanceWorkStatusPage(ViewOpsMaintenanceWorkStatusPageReqVO pageReqVO) {
        return viewOpsMaintenanceWorkStatusMapper.selectPage(pageReqVO);
    }

}