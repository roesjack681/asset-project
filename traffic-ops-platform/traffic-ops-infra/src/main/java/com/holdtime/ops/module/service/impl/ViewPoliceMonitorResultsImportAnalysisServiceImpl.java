package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewPoliceMonitorResultsImportAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewPoliceMonitorResultsImportAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewPoliceMonitorResultsImportAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * VIEW Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewPoliceMonitorResultsImportAnalysisServiceImpl implements ViewPoliceMonitorResultsImportAnalysisService {

    @Resource
    private ViewPoliceMonitorResultsImportAnalysisMapper viewPoliceMonitorResultsImportAnalysisMapper;

    @Override
    public String createViewPoliceMonitorResultsImportAnalysis(ViewPoliceMonitorResultsImportAnalysisSaveReqVO createReqVO) {
        // 插入
        ViewPoliceMonitorResultsImportAnalysisDO viewPoliceMonitorResultsImportAnalysis = BeanUtils.toBean(createReqVO, ViewPoliceMonitorResultsImportAnalysisDO.class);
        viewPoliceMonitorResultsImportAnalysisMapper.insert(viewPoliceMonitorResultsImportAnalysis);
        // 返回
        return viewPoliceMonitorResultsImportAnalysis.getEquId();
    }

    @Override
    public void updateViewPoliceMonitorResultsImportAnalysis(ViewPoliceMonitorResultsImportAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateViewPoliceMonitorResultsImportAnalysisExists(updateReqVO.getEquId());
        // 更新
        ViewPoliceMonitorResultsImportAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, ViewPoliceMonitorResultsImportAnalysisDO.class);
        viewPoliceMonitorResultsImportAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewPoliceMonitorResultsImportAnalysis(String id) {
        // 校验存在
        validateViewPoliceMonitorResultsImportAnalysisExists(id);
        // 删除
        viewPoliceMonitorResultsImportAnalysisMapper.deleteById(id);
    }

    private void validateViewPoliceMonitorResultsImportAnalysisExists(String id) {
        if (viewPoliceMonitorResultsImportAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_IMPORT_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public ViewPoliceMonitorResultsImportAnalysisDO getViewPoliceMonitorResultsImportAnalysis(String id) {
        return viewPoliceMonitorResultsImportAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<ViewPoliceMonitorResultsImportAnalysisDO> getViewPoliceMonitorResultsImportAnalysisPage(ViewPoliceMonitorResultsImportAnalysisPageReqVO pageReqVO) {
        return viewPoliceMonitorResultsImportAnalysisMapper.selectPage(pageReqVO);
    }

}