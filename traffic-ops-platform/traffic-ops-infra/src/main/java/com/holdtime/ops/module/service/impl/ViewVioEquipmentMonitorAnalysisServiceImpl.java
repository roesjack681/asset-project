package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewVioEquipmentMonitorAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewVioEquipmentMonitorAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewVioEquipmentMonitorAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 违法抓拍设备监控分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewVioEquipmentMonitorAnalysisServiceImpl implements ViewVioEquipmentMonitorAnalysisService {

    @Resource
    private ViewVioEquipmentMonitorAnalysisMapper viewVioEquipmentMonitorAnalysisMapper;

    @Override
    public String createViewVioEquipmentMonitorAnalysis(ViewVioEquipmentMonitorAnalysisSaveReqVO createReqVO) {
        // 插入
        ViewVioEquipmentMonitorAnalysisDO viewVioEquipmentMonitorAnalysis = BeanUtils.toBean(createReqVO, ViewVioEquipmentMonitorAnalysisDO.class);
        viewVioEquipmentMonitorAnalysisMapper.insert(viewVioEquipmentMonitorAnalysis);
        // 返回
        return viewVioEquipmentMonitorAnalysis.getEquId();
    }

    @Override
    public void updateViewVioEquipmentMonitorAnalysis(ViewVioEquipmentMonitorAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateViewVioEquipmentMonitorAnalysisExists(updateReqVO.getEquId());
        // 更新
        ViewVioEquipmentMonitorAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, ViewVioEquipmentMonitorAnalysisDO.class);
        viewVioEquipmentMonitorAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewVioEquipmentMonitorAnalysis(String id) {
        // 校验存在
        validateViewVioEquipmentMonitorAnalysisExists(id);
        // 删除
        viewVioEquipmentMonitorAnalysisMapper.deleteById(id);
    }

    private void validateViewVioEquipmentMonitorAnalysisExists(String id) {
        if (viewVioEquipmentMonitorAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIEW_VIO_EQUIPMENT_MONITOR_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public ViewVioEquipmentMonitorAnalysisDO getViewVioEquipmentMonitorAnalysis(String id) {
        return viewVioEquipmentMonitorAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<ViewVioEquipmentMonitorAnalysisDO> getViewVioEquipmentMonitorAnalysisPage(ViewVioEquipmentMonitorAnalysisPageReqVO pageReqVO) {
        return viewVioEquipmentMonitorAnalysisMapper.selectPage(pageReqVO);
    }

}