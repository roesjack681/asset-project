package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficVioEquipmentInvalidReasonAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentInvalidReasonAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficVioEquipmentInvalidReasonAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指设备错误原因分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficVioEquipmentInvalidReasonAnalysisServiceImpl implements TrafficVioEquipmentInvalidReasonAnalysisService {

    @Resource
    private TrafficVioEquipmentInvalidReasonAnalysisMapper vioEquipmentInvalidReasonAnalysisMapper;

    @Override
    public String createVioEquipmentInvalidReasonAnalysis(TrafficVioEquipmentInvalidReasonAnalysisSaveReqVO createReqVO) {
        // 插入
        TrafficVioEquipmentInvalidReasonAnalysisDO vioEquipmentInvalidReasonAnalysis = BeanUtils.toBean(createReqVO, TrafficVioEquipmentInvalidReasonAnalysisDO.class);
        vioEquipmentInvalidReasonAnalysisMapper.insert(vioEquipmentInvalidReasonAnalysis);
        // 返回
        return vioEquipmentInvalidReasonAnalysis.getId();
    }

    @Override
    public void updateVioEquipmentInvalidReasonAnalysis(TrafficVioEquipmentInvalidReasonAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateVioEquipmentInvalidReasonAnalysisExists(updateReqVO.getId());
        // 更新
        TrafficVioEquipmentInvalidReasonAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioEquipmentInvalidReasonAnalysisDO.class);
        vioEquipmentInvalidReasonAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteVioEquipmentInvalidReasonAnalysis(String id) {
        // 校验存在
        validateVioEquipmentInvalidReasonAnalysisExists(id);
        // 删除
        vioEquipmentInvalidReasonAnalysisMapper.deleteById(id);
    }

    private void validateVioEquipmentInvalidReasonAnalysisExists(String id) {
        if (vioEquipmentInvalidReasonAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_EQUIPMENT_INVALID_REASON_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficVioEquipmentInvalidReasonAnalysisDO getVioEquipmentInvalidReasonAnalysis(String id) {
        return vioEquipmentInvalidReasonAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioEquipmentInvalidReasonAnalysisDO> getVioEquipmentInvalidReasonAnalysisPage(TrafficVioEquipmentInvalidReasonAnalysisPageReqVO pageReqVO) {
        return vioEquipmentInvalidReasonAnalysisMapper.selectPage(pageReqVO);
    }

}