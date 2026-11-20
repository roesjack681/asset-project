package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyAnalysisDO;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDO;
import com.holdtime.ops.module.model.dto.TrafficOpsMemeberCameraDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorDetailResultDTO;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Service 接口
 *
 * @author 管理员
 */
public interface ViewOpsMonitorResultsExtService {
    /**
     * 获得VIEW分页
     *
     * @param pageReqVO 分页查询
     * @return VIEW分页
     */
    PageResult<ViewOpsMonitorResultsExtRespVO> getViewOpsMonitorResultsPage(ViewOpsMonitorResultsPageReqVO pageReqVO);

    /**
     * 分析设备监测结果
     * @param page 分页信息
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 检测分析结果
     */
    PageResult<TrafficOpsMonitorDetailResultDTO> analysisMonitorDetailResult(Page<?> page, String ipAddr, String eventType,
                                                                             String startDate, String endDate);
    /**
     * 获得设备监测结果每日分析分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果每日分析分页
     */
    PageResult<TrafficMonitorResultsDailyAnalysisExtRespVO> getMonitorResultsDailyAnalysisPage(TrafficMonitorResultsDailyAnalysisExtPageReqVO pageReqVO);

}