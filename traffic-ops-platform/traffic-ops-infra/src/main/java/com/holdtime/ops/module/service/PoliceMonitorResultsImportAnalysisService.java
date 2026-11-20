package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 公安监测结果导入分析 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorResultsImportAnalysisService {

    /**
     * 创建公安监测结果导入分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsImportAnalysis(@Valid PoliceMonitorResultsImportAnalysisSaveReqVO createReqVO);

    /**
     * 更新公安监测结果导入分析
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsImportAnalysis(@Valid PoliceMonitorResultsImportAnalysisSaveReqVO updateReqVO);

    /**
     * 删除公安监测结果导入分析
     *
     * @param id 编号
     */
    void deleteMonitorResultsImportAnalysis(String id);

    /**
     * 获得公安监测结果导入分析
     *
     * @param id 编号
     * @return 公安监测结果导入分析
     */
    PoliceMonitorResultsImportAnalysisDO getMonitorResultsImportAnalysis(String id);

    /**
     * 获得公安监测结果导入分析分页
     *
     * @param pageReqVO 分页查询
     * @return 公安监测结果导入分析分页
     */
    PageResult<PoliceMonitorResultsImportAnalysisDO> getMonitorResultsImportAnalysisPage(PoliceMonitorResultsImportAnalysisPageReqVO pageReqVO);

}