package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewOpsMonitorResultsDailyAnalysisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDailyAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewOpsMonitorResultsDailyAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 检测结果每日分析视图 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewOpsMonitorResultsDailyAnalysisServiceImpl implements ViewOpsMonitorResultsDailyAnalysisService {

    @Resource
    private ViewOpsMonitorResultsDailyAnalysisMapper viewOpsMonitorResultsDailyAnalysisMapper;

    @Override
    public String createViewOpsMonitorResultsDailyAnalysis(ViewOpsMonitorResultsDailyAnalysisSaveReqVO createReqVO) {
        // 插入
        ViewOpsMonitorResultsDailyAnalysisDO viewOpsMonitorResultsDailyAnalysis = BeanUtils.toBean(createReqVO, ViewOpsMonitorResultsDailyAnalysisDO.class);
        viewOpsMonitorResultsDailyAnalysisMapper.insert(viewOpsMonitorResultsDailyAnalysis);
        // 返回
        return viewOpsMonitorResultsDailyAnalysis.getEquId();
    }

    @Override
    public void updateViewOpsMonitorResultsDailyAnalysis(ViewOpsMonitorResultsDailyAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateViewOpsMonitorResultsDailyAnalysisExists(updateReqVO.getEquId());
        // 更新
        ViewOpsMonitorResultsDailyAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, ViewOpsMonitorResultsDailyAnalysisDO.class);
        viewOpsMonitorResultsDailyAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewOpsMonitorResultsDailyAnalysis(String id) {
        // 校验存在
        validateViewOpsMonitorResultsDailyAnalysisExists(id);
        // 删除
        viewOpsMonitorResultsDailyAnalysisMapper.deleteById(id);
    }

    private void validateViewOpsMonitorResultsDailyAnalysisExists(String id) {
        if (viewOpsMonitorResultsDailyAnalysisMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_DAILY_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public ViewOpsMonitorResultsDailyAnalysisDO getViewOpsMonitorResultsDailyAnalysis(String id) {
        return viewOpsMonitorResultsDailyAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<ViewOpsMonitorResultsDailyAnalysisDO> getViewOpsMonitorResultsDailyAnalysisPage(ViewOpsMonitorResultsDailyAnalysisPageReqVO pageReqVO) {
        return viewOpsMonitorResultsDailyAnalysisMapper.selectPage(pageReqVO);
    }

}