package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorAnalysisDO;
import com.holdtime.ops.module.model.vo.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 集指设备检测分析 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioEquipmentMonitorAnalysisExtService {

    /**
     * 获得集指设备检测分析
     *
     * @param id 编号
     * @return 集指设备检测分析
     */
    TrafficVioEquipmentMonitorAnalysisDO getVioEquipmentMonitorAnalysis(String id);

    /**
     * 获得集指设备检测分析分页
     *
     * @param pageReqVO 分页查询
     * @return 集指设备检测分析分页
     */
    PageResult<TrafficVioEquipmentMonitorAnalysisExtRespVO> getVioEquipmentMonitorAnalysisPage(TrafficVioEquipmentMonitorAnalysisExtPageReqVO pageReqVO);

    /**
     * 分析违法设备抓拍情况
     * @param date 分析日期
     */
    void analysisVioEquipmentSurveil(Date date);

    /**
     * 分析无效原因饼图
     * @param sbbh 设备编号
     * @param analysisDay 分析时间
     * @return
     */
    List<Map<String, Collection<Integer>>> analysisVioInvalidReasonPieChart(String sbbh, String analysisDay);

    /**
     * 分析无效原因饼图
     * @param sbbh 设备编号
     * @return
     */
    List<Map<String, Object>> analysisVioValidLineChart(String sbbh);

    /**
     * 保存备注信息
     * @param saveReqVO 报错内容
     */
    void remarkVioEquipmentMonitor(TrafficVioEquipmentMonitorRemarkSaveReqVO saveReqVO);

    /**
     * 分析有效违法行为饼图
     * @param sbbh 设备编号
     * @param analysisDay 分析时间
     * @return
     */
    List<Map<String, Collection<Integer>>> analysisVioIllegalPieChart(String sbbh, String analysisDay);
}