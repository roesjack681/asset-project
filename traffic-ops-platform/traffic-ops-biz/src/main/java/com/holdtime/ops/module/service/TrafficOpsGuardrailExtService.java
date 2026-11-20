package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailExtSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMarklineExtSaveReqVO;

import javax.validation.Valid;

/**
 * 护栏 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsGuardrailExtService {

    /**
     * 保存护栏
     *
     * @param saveReqVO 护栏信息
     * @return 编号
     */
    String saveOpsGuardrail(@Valid TrafficOpsGuardrailExtSaveReqVO saveReqVO);

    /**
     * 获得护栏
     *
     * @param id 编号
     * @return 护栏
     */
    TrafficOpsGuardrailExtRespVO getOpsGuardrail(String id);

}