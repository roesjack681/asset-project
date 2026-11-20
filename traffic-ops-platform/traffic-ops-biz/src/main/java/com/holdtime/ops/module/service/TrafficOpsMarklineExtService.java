package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.TrafficOpsMarklineExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMarklineExtSaveReqVO;

import javax.validation.Valid;

/**
 * 标线 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMarklineExtService {

    /**
     * 创建标线
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String saveOpsMarkline(@Valid TrafficOpsMarklineExtSaveReqVO createReqVO);

    /**
     * 获得标线
     *
     * @param id 编号
     * @return 标线
     */
    TrafficOpsMarklineExtRespVO getOpsMarkline(String id);
}