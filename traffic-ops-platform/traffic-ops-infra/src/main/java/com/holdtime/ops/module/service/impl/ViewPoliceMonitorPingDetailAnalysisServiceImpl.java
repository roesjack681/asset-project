package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewPoliceMonitorPingDetailAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewPoliceMonitorPingDetailAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewPoliceMonitorPingDetailAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * VIEW Service 实现类
 *
 * @author yanxl
 */
@Service
@Validated
public class ViewPoliceMonitorPingDetailAnalysisServiceImpl implements ViewPoliceMonitorPingDetailAnalysisService {

    @Resource
    private ViewPoliceMonitorPingDetailAnalysisMapper viewPoliceMonitorPingDetailAnalysisMapper;

    @Override
    public String createViewPoliceMonitorPingDetailAnalysis(ViewPoliceMonitorPingDetailAnalysisSaveReqVO createReqVO) {
        // 插入
        ViewPoliceMonitorPingDetailAnalysisDO viewPoliceMonitorPingDetailAnalysis = BeanUtils.toBean(createReqVO, ViewPoliceMonitorPingDetailAnalysisDO.class);
        viewPoliceMonitorPingDetailAnalysisMapper.insert(viewPoliceMonitorPingDetailAnalysis);
        // 返回
        return viewPoliceMonitorPingDetailAnalysis.getEquId();
    }

    @Override
    public void updateViewPoliceMonitorPingDetailAnalysis(ViewPoliceMonitorPingDetailAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateViewPoliceMonitorPingDetailAnalysisExists(updateReqVO.getEquId());
        // 更新
        ViewPoliceMonitorPingDetailAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, ViewPoliceMonitorPingDetailAnalysisDO.class);
        viewPoliceMonitorPingDetailAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewPoliceMonitorPingDetailAnalysis(String id) {
        // 校验存在
        validateViewPoliceMonitorPingDetailAnalysisExists(id);
        // 删除
        viewPoliceMonitorPingDetailAnalysisMapper.deleteById(id);
    }

    private void validateViewPoliceMonitorPingDetailAnalysisExists(String id) {
        if (viewPoliceMonitorPingDetailAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_PING_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public ViewPoliceMonitorPingDetailAnalysisDO getViewPoliceMonitorPingDetailAnalysis(String id) {
        return viewPoliceMonitorPingDetailAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<ViewPoliceMonitorPingDetailAnalysisDO> getViewPoliceMonitorPingDetailAnalysisPage(ViewPoliceMonitorPingDetailAnalysisPageReqVO pageReqVO) {
        return viewPoliceMonitorPingDetailAnalysisMapper.selectPage(pageReqVO);
    }

}