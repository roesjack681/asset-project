package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewVioSurveilAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewVioSurveilAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewVioSurveilAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * VIEW Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewVioSurveilAnalysisServiceImpl implements ViewVioSurveilAnalysisService {

    @Resource
    private ViewVioSurveilAnalysisMapper viewVioSurveilAnalysisMapper;

    @Override
    public String createViewVioSurveilAnalysis(ViewVioSurveilAnalysisSaveReqVO createReqVO) {
        // 插入
        ViewVioSurveilAnalysisDO viewVioSurveilAnalysis = BeanUtils.toBean(createReqVO, ViewVioSurveilAnalysisDO.class);
        viewVioSurveilAnalysisMapper.insert(viewVioSurveilAnalysis);
        // 返回
        return viewVioSurveilAnalysis.getSbbh();
    }

    @Override
    public void updateViewVioSurveilAnalysis(ViewVioSurveilAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateViewVioSurveilAnalysisExists(updateReqVO.getSbbh());
        // 更新
        ViewVioSurveilAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, ViewVioSurveilAnalysisDO.class);
        viewVioSurveilAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewVioSurveilAnalysis(String id) {
        // 校验存在
        validateViewVioSurveilAnalysisExists(id);
        // 删除
        viewVioSurveilAnalysisMapper.deleteById(id);
    }

    private void validateViewVioSurveilAnalysisExists(String id) {
        if (viewVioSurveilAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_SURVEIL_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public ViewVioSurveilAnalysisDO getViewVioSurveilAnalysis(String id) {
        return viewVioSurveilAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<ViewVioSurveilAnalysisDO> getViewVioSurveilAnalysisPage(ViewVioSurveilAnalysisPageReqVO pageReqVO) {
        return viewVioSurveilAnalysisMapper.selectPage(pageReqVO);
    }

}