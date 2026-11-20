package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.ViewOpsMonitorResultsDailyAnalysisPageReqVO;
import com.holdtime.ops.module.model.vo.ViewOpsMonitorResultsDailyAnalysisRespVO;

/**
 * 检测结果每日分析视图 Service 接口
 *
 * @author 管理员
 */
public interface ViewOpsMonitorResultsDailyAnalysisExtService {
    /**
     * 获得检测结果每日分析视图分页
     *
     * @param pageReqVO 分页查询
     * @return 检测结果每日分析视图分页
     */
    PageResult<ViewOpsMonitorResultsDailyAnalysisRespVO> getViewOpsMonitorResultsDailyAnalysisPage(ViewOpsMonitorResultsDailyAnalysisPageReqVO pageReqVO);

}