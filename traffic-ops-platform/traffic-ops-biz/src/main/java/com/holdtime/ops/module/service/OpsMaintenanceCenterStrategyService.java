package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.model.vo.*;

/**
 * 维保调度中心接口
 *
 * @author 芋道源码
 */
public interface OpsMaintenanceCenterStrategyService {
    /**
     * 分派维修处理内容
     *
     * @param opsMaintenanceDO 维修记录
     * @param progressVO       维修过程信息
     */
    void assignMaintenanceHandler(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO);

}