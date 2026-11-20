package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentInvalidReasonAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 集指设备错误原因分析 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioEquipmentInvalidReasonAnalysisService {

    /**
     * 创建集指设备错误原因分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createVioEquipmentInvalidReasonAnalysis(@Valid TrafficVioEquipmentInvalidReasonAnalysisSaveReqVO createReqVO);

    /**
     * 更新集指设备错误原因分析
     *
     * @param updateReqVO 更新信息
     */
    void updateVioEquipmentInvalidReasonAnalysis(@Valid TrafficVioEquipmentInvalidReasonAnalysisSaveReqVO updateReqVO);

    /**
     * 删除集指设备错误原因分析
     *
     * @param id 编号
     */
    void deleteVioEquipmentInvalidReasonAnalysis(String id);

    /**
     * 获得集指设备错误原因分析
     *
     * @param id 编号
     * @return 集指设备错误原因分析
     */
    TrafficVioEquipmentInvalidReasonAnalysisDO getVioEquipmentInvalidReasonAnalysis(String id);

    /**
     * 获得集指设备错误原因分析分页
     *
     * @param pageReqVO 分页查询
     * @return 集指设备错误原因分析分页
     */
    PageResult<TrafficVioEquipmentInvalidReasonAnalysisDO> getVioEquipmentInvalidReasonAnalysisPage(TrafficVioEquipmentInvalidReasonAnalysisPageReqVO pageReqVO);

}