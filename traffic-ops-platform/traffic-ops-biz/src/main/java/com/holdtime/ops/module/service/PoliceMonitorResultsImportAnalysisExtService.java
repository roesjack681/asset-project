package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.PoliceMonitorPingDetailDTO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsImportAnalysisExtPageReqVO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsImportAnalysisExtRespVO;

import java.time.LocalDateTime;

/**
 * 公安监测结果导入分析 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorResultsImportAnalysisExtService {

    /**
     * 分析公安监测导入结果
     */
    void analysisMonitorResultsImportDetail(LocalDateTime occurrenceTime);

    /**
     * 获得公安监测结果导入分析分页
     *
     * @param pageReqVO 分页查询
     * @return 公安监测结果导入分析分页
     */
    PageResult<PoliceMonitorResultsImportAnalysisExtRespVO> getMonitorResultsImportAnalysisPage(PoliceMonitorResultsImportAnalysisExtPageReqVO pageReqVO);

    /**
     * 查询华智监测通断分析
     * @param pageReqVO 请求参数
     * @return 分析结果
     */
    PageResult<PoliceMonitorResultsImportAnalysisExtRespVO> queryHuazhiMonitorOnOffResultPage(PoliceMonitorResultsImportAnalysisExtPageReqVO pageReqVO);
}