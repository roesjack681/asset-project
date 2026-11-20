package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.TrafficLightsdetectionlogDO;
import com.holdtime.ops.module.model.vo.TrafficLightsdetectionlogPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficLightsdetectionlogSaveReqVO;

import javax.validation.Valid;

/**
 * 信号灯检测信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficLightsdetectionlogExtService {

    /**
     * 创建信号灯检测信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLightsdetectionlog(@Valid TrafficLightsdetectionlogSaveReqVO createReqVO);


}