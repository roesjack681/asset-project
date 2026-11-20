package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewPoliceMonitorPingDetailSituationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewPoliceMonitorPingDetailSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewPoliceMonitorPingDetailSituationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * VIEW Service 实现类
 *
 * @author yanxl
 */
@Service
@Validated
public class ViewPoliceMonitorPingDetailSituationServiceImpl implements ViewPoliceMonitorPingDetailSituationService {

    @Resource
    private ViewPoliceMonitorPingDetailSituationMapper viewPoliceMonitorPingDetailSituationMapper;

    @Override
    public String createViewPoliceMonitorPingDetailSituation(ViewPoliceMonitorPingDetailSituationSaveReqVO createReqVO) {
        // 插入
        ViewPoliceMonitorPingDetailSituationDO viewPoliceMonitorPingDetailSituation = BeanUtils.toBean(createReqVO, ViewPoliceMonitorPingDetailSituationDO.class);
        viewPoliceMonitorPingDetailSituationMapper.insert(viewPoliceMonitorPingDetailSituation);
        // 返回
        return viewPoliceMonitorPingDetailSituation.getEquId();
    }

    @Override
    public void updateViewPoliceMonitorPingDetailSituation(ViewPoliceMonitorPingDetailSituationSaveReqVO updateReqVO) {
        // 校验存在
        validateViewPoliceMonitorPingDetailSituationExists(updateReqVO.getEquId());
        // 更新
        ViewPoliceMonitorPingDetailSituationDO updateObj = BeanUtils.toBean(updateReqVO, ViewPoliceMonitorPingDetailSituationDO.class);
        viewPoliceMonitorPingDetailSituationMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewPoliceMonitorPingDetailSituation(String id) {
        // 校验存在
        validateViewPoliceMonitorPingDetailSituationExists(id);
        // 删除
        viewPoliceMonitorPingDetailSituationMapper.deleteById(id);
    }

    private void validateViewPoliceMonitorPingDetailSituationExists(String id) {
        if (viewPoliceMonitorPingDetailSituationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_PING_DETAIL_SITUATION_NOT_EXISTS);
        }
    }

    @Override
    public ViewPoliceMonitorPingDetailSituationDO getViewPoliceMonitorPingDetailSituation(String id) {
        return viewPoliceMonitorPingDetailSituationMapper.selectById(id);
    }

    @Override
    public PageResult<ViewPoliceMonitorPingDetailSituationDO> getViewPoliceMonitorPingDetailSituationPage(ViewPoliceMonitorPingDetailSituationPageReqVO pageReqVO) {
        return viewPoliceMonitorPingDetailSituationMapper.selectPage(pageReqVO);
    }

}