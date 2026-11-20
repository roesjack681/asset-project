package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficVioEquipmentMonitorAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficVioEquipmentMonitorAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指设备检测分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficVioEquipmentMonitorAnalysisServiceImpl implements TrafficVioEquipmentMonitorAnalysisService {

    @Resource
    private TrafficVioEquipmentMonitorAnalysisMapper vioEquipmentMonitorAnalysisMapper;

    @Override
    public String createVioEquipmentMonitorAnalysis(TrafficVioEquipmentMonitorAnalysisSaveReqVO createReqVO) {
        // 插入
        TrafficVioEquipmentMonitorAnalysisDO vioEquipmentMonitorAnalysis = BeanUtils.toBean(createReqVO, TrafficVioEquipmentMonitorAnalysisDO.class);
        vioEquipmentMonitorAnalysisMapper.insert(vioEquipmentMonitorAnalysis);
        // 返回
        return vioEquipmentMonitorAnalysis.getId();
    }

    @Override
    public void updateVioEquipmentMonitorAnalysis(TrafficVioEquipmentMonitorAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateVioEquipmentMonitorAnalysisExists(updateReqVO.getId());
        // 更新
        TrafficVioEquipmentMonitorAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioEquipmentMonitorAnalysisDO.class);
        vioEquipmentMonitorAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteVioEquipmentMonitorAnalysis(String id) {
        // 校验存在
        validateVioEquipmentMonitorAnalysisExists(id);
        // 删除
        vioEquipmentMonitorAnalysisMapper.deleteById(id);
    }

    private void validateVioEquipmentMonitorAnalysisExists(String id) {
        if (vioEquipmentMonitorAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_EQUIPMENT_MONITOR_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficVioEquipmentMonitorAnalysisDO getVioEquipmentMonitorAnalysis(String id) {
        return vioEquipmentMonitorAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioEquipmentMonitorAnalysisDO> getVioEquipmentMonitorAnalysisPage(TrafficVioEquipmentMonitorAnalysisPageReqVO pageReqVO) {
        return vioEquipmentMonitorAnalysisMapper.selectPage(pageReqVO);
    }

}