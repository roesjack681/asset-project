package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsImportAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果导入分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficMonitorResultsImportAnalysisServiceImpl implements TrafficMonitorResultsImportAnalysisService {

    @Resource
    private TrafficMonitorResultsImportAnalysisMapper monitorResultsImportAnalysisMapper;

    @Override
    public String createMonitorResultsImportAnalysis(TrafficMonitorResultsImportAnalysisSaveReqVO createReqVO) {
        // 插入
        TrafficMonitorResultsImportAnalysisDO monitorResultsImportAnalysis = BeanUtils.toBean(createReqVO, TrafficMonitorResultsImportAnalysisDO.class);
        monitorResultsImportAnalysisMapper.insert(monitorResultsImportAnalysis);
        // 返回
        return monitorResultsImportAnalysis.getId();
    }

    @Override
    public void updateMonitorResultsImportAnalysis(TrafficMonitorResultsImportAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsImportAnalysisExists(updateReqVO.getId());
        // 更新
        TrafficMonitorResultsImportAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, TrafficMonitorResultsImportAnalysisDO.class);
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
    public TrafficMonitorResultsImportAnalysisDO getMonitorResultsImportAnalysis(String id) {
        return monitorResultsImportAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficMonitorResultsImportAnalysisDO> getMonitorResultsImportAnalysisPage(TrafficMonitorResultsImportAnalysisPageReqVO pageReqVO) {
        return monitorResultsImportAnalysisMapper.selectPage(pageReqVO);
    }

}