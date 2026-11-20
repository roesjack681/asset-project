package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 集指设备检测分析 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioEquipmentMonitorAnalysisService {

    /**
     * 创建集指设备检测分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createVioEquipmentMonitorAnalysis(@Valid TrafficVioEquipmentMonitorAnalysisSaveReqVO createReqVO);

    /**
     * 更新集指设备检测分析
     *
     * @param updateReqVO 更新信息
     */
    void updateVioEquipmentMonitorAnalysis(@Valid TrafficVioEquipmentMonitorAnalysisSaveReqVO updateReqVO);

    /**
     * 删除集指设备检测分析
     *
     * @param id 编号
     */
    void deleteVioEquipmentMonitorAnalysis(String id);

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
    PageResult<TrafficVioEquipmentMonitorAnalysisDO> getVioEquipmentMonitorAnalysisPage(TrafficVioEquipmentMonitorAnalysisPageReqVO pageReqVO);

}