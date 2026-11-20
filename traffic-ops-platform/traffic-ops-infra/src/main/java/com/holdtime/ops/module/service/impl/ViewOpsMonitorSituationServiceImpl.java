package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewOpsMonitorSituationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMonitorSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewOpsMonitorSituationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
/**
 * VIEW Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewOpsMonitorSituationServiceImpl implements ViewOpsMonitorSituationService {

    @Resource
    private ViewOpsMonitorSituationMapper viewOpsMonitorSituationMapper;

    @Override
    public String createViewOpsMonitorSituation(ViewOpsMonitorSituationSaveReqVO createReqVO) {
        // 插入
        ViewOpsMonitorSituationDO viewOpsMonitorSituation = BeanUtils.toBean(createReqVO, ViewOpsMonitorSituationDO.class);
        viewOpsMonitorSituationMapper.insert(viewOpsMonitorSituation);
        // 返回
        return viewOpsMonitorSituation.getEquId();
    }

    @Override
    public void updateViewOpsMonitorSituation(ViewOpsMonitorSituationSaveReqVO updateReqVO) {
        // 校验存在
        validateViewOpsMonitorSituationExists(updateReqVO.getEquId());
        // 更新
        ViewOpsMonitorSituationDO updateObj = BeanUtils.toBean(updateReqVO, ViewOpsMonitorSituationDO.class);
        viewOpsMonitorSituationMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewOpsMonitorSituation(String id) {
        // 校验存在
        validateViewOpsMonitorSituationExists(id);
        // 删除
        viewOpsMonitorSituationMapper.deleteById(id);
    }

    private void validateViewOpsMonitorSituationExists(String id) {
        if (viewOpsMonitorSituationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIEW_OPS_MONITOR_SITUATION_NOT_EXISTS);
        }
    }

    @Override
    public ViewOpsMonitorSituationDO getViewOpsMonitorSituation(String id) {
        return viewOpsMonitorSituationMapper.selectById(id);
    }

    @Override
    public PageResult<ViewOpsMonitorSituationDO> getViewOpsMonitorSituationPage(ViewOpsMonitorSituationPageReqVO pageReqVO) {
        return viewOpsMonitorSituationMapper.selectPage(pageReqVO);
    }

}