package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorPingAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorPingAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorPingAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 电警通断检测管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorPingAnalysisServiceImpl implements PoliceMonitorPingAnalysisService {

    @Resource
    private PoliceMonitorPingAnalysisMapper monitorPingAnalysisMapper;

    @Override
    public String createMonitorPingAnalysis(PoliceMonitorPingAnalysisSaveReqVO createReqVO) {
        // 插入
        PoliceMonitorPingAnalysisDO monitorPingAnalysis = BeanUtils.toBean(createReqVO, PoliceMonitorPingAnalysisDO.class);
        monitorPingAnalysisMapper.insert(monitorPingAnalysis);
        // 返回
        return monitorPingAnalysis.getId();
    }

    @Override
    public void updateMonitorPingAnalysis(PoliceMonitorPingAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorPingAnalysisExists(updateReqVO.getId());
        // 更新
        PoliceMonitorPingAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorPingAnalysisDO.class);
        monitorPingAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorPingAnalysis(String id) {
        // 校验存在
        validateMonitorPingAnalysisExists(id);
        // 删除
        monitorPingAnalysisMapper.deleteById(id);
    }

    private void validateMonitorPingAnalysisExists(String id) {
        if (monitorPingAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_PING_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public PoliceMonitorPingAnalysisDO getMonitorPingAnalysis(String id) {
        return monitorPingAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorPingAnalysisDO> getMonitorPingAnalysisPage(PoliceMonitorPingAnalysisPageReqVO pageReqVO) {
        return monitorPingAnalysisMapper.selectPage(pageReqVO);
    }

}