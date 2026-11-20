package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.TrafficOpsMaintainFlowRecordDO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainFlowRecordSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备维保全流程 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsMaintainFlowRecordExtService {

    /**
     * 创建设备维保全流程
     *
     * @param createReqVO 创建信息
     */
    void saveMaintainFlowRecord(@Valid TrafficOpsMaintainFlowRecordSaveReqVO createReqVO);

    /**
     * 批量保存维修流程
     *
     * @param createReqVO 创建信息
     */
    void batchSaveMaintainFlowRecord(List<String> maintainIdList, @Valid TrafficOpsMaintainFlowRecordSaveReqVO createReqVO);

    /**
     * 获得设备维保全流程
     *
     * @param maintainId 报修记录ID
     * @return 设备维保全流程
     */
    List<TrafficOpsMaintainFlowRecordDO> getOpsMaintainFlowRecord(String maintainId);
}