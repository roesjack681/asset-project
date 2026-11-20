package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficVioEquipmentIllegalAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentIllegalAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficVioEquipmentIllegalAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指设备违法原因分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficVioEquipmentIllegalAnalysisServiceImpl implements TrafficVioEquipmentIllegalAnalysisService {

    @Resource
    private TrafficVioEquipmentIllegalAnalysisMapper vioEquipmentIllegalAnalysisMapper;

    @Override
    public String createVioEquipmentIllegalAnalysis(TrafficVioEquipmentIllegalAnalysisSaveReqVO createReqVO) {
        // 插入
        TrafficVioEquipmentIllegalAnalysisDO vioEquipmentIllegalAnalysis = BeanUtils.toBean(createReqVO, TrafficVioEquipmentIllegalAnalysisDO.class);
        vioEquipmentIllegalAnalysisMapper.insert(vioEquipmentIllegalAnalysis);
        // 返回
        return vioEquipmentIllegalAnalysis.getId();
    }

    @Override
    public void updateVioEquipmentIllegalAnalysis(TrafficVioEquipmentIllegalAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateVioEquipmentIllegalAnalysisExists(updateReqVO.getId());
        // 更新
        TrafficVioEquipmentIllegalAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioEquipmentIllegalAnalysisDO.class);
        vioEquipmentIllegalAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteVioEquipmentIllegalAnalysis(String id) {
        // 校验存在
        validateVioEquipmentIllegalAnalysisExists(id);
        // 删除
        vioEquipmentIllegalAnalysisMapper.deleteById(id);
    }

    private void validateVioEquipmentIllegalAnalysisExists(String id) {
        if (vioEquipmentIllegalAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_EQUIPMENT_ILLEGAL_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficVioEquipmentIllegalAnalysisDO getVioEquipmentIllegalAnalysis(String id) {
        return vioEquipmentIllegalAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioEquipmentIllegalAnalysisDO> getVioEquipmentIllegalAnalysisPage(TrafficVioEquipmentIllegalAnalysisPageReqVO pageReqVO) {
        return vioEquipmentIllegalAnalysisMapper.selectPage(pageReqVO);
    }

}