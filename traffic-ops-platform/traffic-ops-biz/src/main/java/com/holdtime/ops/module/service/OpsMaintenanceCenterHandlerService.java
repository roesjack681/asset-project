package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainProgressSaveReqVO;

/**
 * 维保处理类接口
 *
 * @author 芋道源码
 */
public interface OpsMaintenanceCenterHandlerService {
    /**
     * 护栏处理
     * @param progressVO 维护内容
     */
    String handlerGuardrail(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO);

}