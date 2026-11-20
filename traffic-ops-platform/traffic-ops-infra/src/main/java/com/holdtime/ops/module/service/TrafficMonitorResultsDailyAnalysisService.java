package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备监测结果每日分析 Service 接口
 *
 * @author 管理员
 */
public interface TrafficMonitorResultsDailyAnalysisService {

    /**
     * 创建设备监测结果每日分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsDailyAnalysis(@Valid TrafficMonitorResultsDailyAnalysisSaveReqVO createReqVO);

    /**
     * 更新设备监测结果每日分析
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsDailyAnalysis(@Valid TrafficMonitorResultsDailyAnalysisSaveReqVO updateReqVO);

    /**
     * 删除设备监测结果每日分析
     *
     * @param id 编号
     */
    void deleteMonitorResultsDailyAnalysis(String id);

    /**
     * 获得设备监测结果每日分析
     *
     * @param id 编号
     * @return 设备监测结果每日分析
     */
    TrafficMonitorResultsDailyAnalysisDO getMonitorResultsDailyAnalysis(String id);

    /**
     * 获得设备监测结果每日分析分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果每日分析分页
     */
    PageResult<TrafficMonitorResultsDailyAnalysisDO> getMonitorResultsDailyAnalysisPage(TrafficMonitorResultsDailyAnalysisPageReqVO pageReqVO);

}