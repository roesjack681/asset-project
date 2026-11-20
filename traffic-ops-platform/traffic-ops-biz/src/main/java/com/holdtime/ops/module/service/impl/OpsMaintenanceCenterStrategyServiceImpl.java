package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainProgressSaveReqVO;
import com.holdtime.ops.module.service.OpsMaintenanceCenterHandlerService;
import com.holdtime.ops.module.service.OpsMaintenanceCenterStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Service
@Slf4j
public class OpsMaintenanceCenterStrategyServiceImpl implements OpsMaintenanceCenterStrategyService {
    @Resource
    private OpsMaintenanceCenterHandlerService opsMaintenanceCenterHandlerService;

    private final Map<String, BiFunction<TrafficOpsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO, String>> strategyFunctionMap = new HashMap<>();

    @PostConstruct
    public void dispatcherInit(){
//        // 电警维修策略
//        strategyFunctionMap.put(OpsConstants.EQU_TYPE_DJ, (TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO) -> opsMaintenanceCenterHandlerService.handlerElectricPolice(opsMaintenanceDO, progressVO));

//        // 杆件维修策略
//        strategyConsumerMap.put(OpsConstants.EQU_TYPE_GJ, (TrafficOpsMaintainProgressSaveReqVO progressVO) -> expenseTransportHandlerService.calcShippingCostForOneWeight(poiDivisionId, transportGoodsMap));

        // 护栏维修策略
        strategyFunctionMap.put(OpsConstants.EQU_TYPE_GUARDRAIL, (TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO) -> opsMaintenanceCenterHandlerService.handlerGuardrail(opsMaintenanceDO, progressVO));
    }

    @Override
    public void assignMaintenanceHandler(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO) {
        BiFunction<TrafficOpsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO, String> strategyFunction = strategyFunctionMap.get(progressVO.getEquType());
        if (strategyFunction == null) {
            log.warn("未找到该设备类型[{}]的处理类", progressVO.getEquType());
            return;
        }
        strategyFunction.apply(opsMaintenanceDO, progressVO);
    }
}
