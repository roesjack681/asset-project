package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorResultsImportAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安监测结果导入分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorResultsImportAnalysisServiceImpl implements PoliceMonitorResultsImportAnalysisService {

    @Resource
    private PoliceMonitorResultsImportAnalysisMapper monitorResultsImportAnalysisMapper;

    @Override
    public String createMonitorResultsImportAnalysis(PoliceMonitorResultsImportAnalysisSaveReqVO createReqVO) {
        // 插入
        PoliceMonitorResultsImportAnalysisDO monitorResultsImportAnalysis = BeanUtils.toBean(createReqVO, PoliceMonitorResultsImportAnalysisDO.class);
        monitorResultsImportAnalysisMapper.insert(monitorResultsImportAnalysis);
        // 返回
        return monitorResultsImportAnalysis.getId();
    }

    @Override
    public void updateMonitorResultsImportAnalysis(PoliceMonitorResultsImportAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsImportAnalysisExists(updateReqVO.getId());
        // 更新
        PoliceMonitorResultsImportAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorResultsImportAnalysisDO.class);
        monitorResultsImportAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResultsImportAnalysis(String id) {
        // 校验存在
        validateMonitorResultsImportAnalysisExists(id);
        // 删除
        monitorResultsImportAnalysisMapper.deleteById(id);
    }

    private void validateMonitorResultsImportAnalysisExists(String id) {
        if (monitorResultsImportAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_IMPORT_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public PoliceMonitorResultsImportAnalysisDO getMonitorResultsImportAnalysis(String id) {
        return monitorResultsImportAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorResultsImportAnalysisDO> getMonitorResultsImportAnalysisPage(PoliceMonitorResultsImportAnalysisPageReqVO pageReqVO) {
        return monitorResultsImportAnalysisMapper.selectPage(pageReqVO);
    }

}