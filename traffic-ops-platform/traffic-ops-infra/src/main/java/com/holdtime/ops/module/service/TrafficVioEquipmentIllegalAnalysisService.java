package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentIllegalAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 集指设备违法原因分析 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioEquipmentIllegalAnalysisService {

    /**
     * 创建集指设备违法原因分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createVioEquipmentIllegalAnalysis(@Valid TrafficVioEquipmentIllegalAnalysisSaveReqVO createReqVO);

    /**
     * 更新集指设备违法原因分析
     *
     * @param updateReqVO 更新信息
     */
    void updateVioEquipmentIllegalAnalysis(@Valid TrafficVioEquipmentIllegalAnalysisSaveReqVO updateReqVO);

    /**
     * 删除集指设备违法原因分析
     *
     * @param id 编号
     */
    void deleteVioEquipmentIllegalAnalysis(String id);

    /**
     * 获得集指设备违法原因分析
     *
     * @param id 编号
     * @return 集指设备违法原因分析
     */
    TrafficVioEquipmentIllegalAnalysisDO getVioEquipmentIllegalAnalysis(String id);

    /**
     * 获得集指设备违法原因分析分页
     *
     * @param pageReqVO 分页查询
     * @return 集指设备违法原因分析分页
     */
    PageResult<TrafficVioEquipmentIllegalAnalysisDO> getVioEquipmentIllegalAnalysisPage(TrafficVioEquipmentIllegalAnalysisPageReqVO pageReqVO);

}