package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewVioEquipmentMonitorAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 违法抓拍设备监控分析 Service 接口
 *
 * @author 管理员
 */
public interface ViewVioEquipmentMonitorAnalysisService {

    /**
     * 创建违法抓拍设备监控分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createViewVioEquipmentMonitorAnalysis(@Valid ViewVioEquipmentMonitorAnalysisSaveReqVO createReqVO);

    /**
     * 更新违法抓拍设备监控分析
     *
     * @param updateReqVO 更新信息
     */
    void updateViewVioEquipmentMonitorAnalysis(@Valid ViewVioEquipmentMonitorAnalysisSaveReqVO updateReqVO);

    /**
     * 删除违法抓拍设备监控分析
     *
     * @param id 编号
     */
    void deleteViewVioEquipmentMonitorAnalysis(String id);

    /**
     * 获得违法抓拍设备监控分析
     *
     * @param id 编号
     * @return 违法抓拍设备监控分析
     */
    ViewVioEquipmentMonitorAnalysisDO getViewVioEquipmentMonitorAnalysis(String id);

    /**
     * 获得违法抓拍设备监控分析分页
     *
     * @param pageReqVO 分页查询
     * @return 违法抓拍设备监控分析分页
     */
    PageResult<ViewVioEquipmentMonitorAnalysisDO> getViewVioEquipmentMonitorAnalysisPage(ViewVioEquipmentMonitorAnalysisPageReqVO pageReqVO);

}