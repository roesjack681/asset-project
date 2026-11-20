package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDailyAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 检测结果每日分析视图 Service 接口
 *
 * @author 管理员
 */
public interface ViewOpsMonitorResultsDailyAnalysisService {

    /**
     * 创建检测结果每日分析视图
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createViewOpsMonitorResultsDailyAnalysis(@Valid ViewOpsMonitorResultsDailyAnalysisSaveReqVO createReqVO);

    /**
     * 更新检测结果每日分析视图
     *
     * @param updateReqVO 更新信息
     */
    void updateViewOpsMonitorResultsDailyAnalysis(@Valid ViewOpsMonitorResultsDailyAnalysisSaveReqVO updateReqVO);

    /**
     * 删除检测结果每日分析视图
     *
     * @param id 编号
     */
    void deleteViewOpsMonitorResultsDailyAnalysis(String id);

    /**
     * 获得检测结果每日分析视图
     *
     * @param id 编号
     * @return 检测结果每日分析视图
     */
    ViewOpsMonitorResultsDailyAnalysisDO getViewOpsMonitorResultsDailyAnalysis(String id);

    /**
     * 获得检测结果每日分析视图分页
     *
     * @param pageReqVO 分页查询
     * @return 检测结果每日分析视图分页
     */
    PageResult<ViewOpsMonitorResultsDailyAnalysisDO> getViewOpsMonitorResultsDailyAnalysisPage(ViewOpsMonitorResultsDailyAnalysisPageReqVO pageReqVO);

}