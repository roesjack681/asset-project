package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficMonitorResultsDailyAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsDailyAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果每日分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficMonitorResultsDailyAnalysisServiceImpl implements TrafficMonitorResultsDailyAnalysisService {

    @Resource
    private TrafficMonitorResultsDailyAnalysisMapper monitorResultsDailyAnalysisMapper;

    @Override
    public String createMonitorResultsDailyAnalysis(TrafficMonitorResultsDailyAnalysisSaveReqVO createReqVO) {
        // 插入
        TrafficMonitorResultsDailyAnalysisDO monitorResultsDailyAnalysis = BeanUtils.toBean(createReqVO, TrafficMonitorResultsDailyAnalysisDO.class);
        monitorResultsDailyAnalysisMapper.insert(monitorResultsDailyAnalysis);
        // 返回
        return monitorResultsDailyAnalysis.getId();
    }

    @Override
    public void updateMonitorResultsDailyAnalysis(TrafficMonitorResultsDailyAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsDailyAnalysisExists(updateReqVO.getId());
        // 更新
        TrafficMonitorResultsDailyAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, TrafficMonitorResultsDailyAnalysisDO.class);
        monitorResultsDailyAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResultsDailyAnalysis(String id) {
        // 校验存在
        validateMonitorResultsDailyAnalysisExists(id);
        // 删除
        monitorResultsDailyAnalysisMapper.deleteById(id);
    }

    private void validateMonitorResultsDailyAnalysisExists(String id) {
        if (monitorResultsDailyAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_DAILY_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficMonitorResultsDailyAnalysisDO getMonitorResultsDailyAnalysis(String id) {
        return monitorResultsDailyAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficMonitorResultsDailyAnalysisDO> getMonitorResultsDailyAnalysisPage(TrafficMonitorResultsDailyAnalysisPageReqVO pageReqVO) {
        return monitorResultsDailyAnalysisMapper.selectPage(pageReqVO);
    }

}