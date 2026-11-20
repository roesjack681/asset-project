package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备监测结果导入分析 Service 接口
 *
 * @author 管理员
 */
public interface TrafficMonitorResultsImportAnalysisService {

    /**
     * 创建设备监测结果导入分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsImportAnalysis(@Valid TrafficMonitorResultsImportAnalysisSaveReqVO createReqVO);

    /**
     * 更新设备监测结果导入分析
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsImportAnalysis(@Valid TrafficMonitorResultsImportAnalysisSaveReqVO updateReqVO);

    /**
     * 删除设备监测结果导入分析
     *
     * @param id 编号
     */
    void deleteMonitorResultsImportAnalysis(String id);

    /**
     * 获得设备监测结果导入分析
     *
     * @param id 编号
     * @return 设备监测结果导入分析
     */
    TrafficMonitorResultsImportAnalysisDO getMonitorResultsImportAnalysis(String id);

    /**
     * 获得设备监测结果导入分析分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果导入分析分页
     */
    PageResult<TrafficMonitorResultsImportAnalysisDO> getMonitorResultsImportAnalysisPage(TrafficMonitorResultsImportAnalysisPageReqVO pageReqVO);

}