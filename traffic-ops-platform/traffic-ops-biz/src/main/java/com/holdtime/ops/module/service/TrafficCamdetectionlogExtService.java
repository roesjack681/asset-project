package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.TrafficCamdetectionlogDO;
import com.holdtime.ops.module.model.vo.TrafficCamdetectionlogPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficCamdetectionlogSaveReqVO;

import javax.validation.Valid;

/**
 * 设备状态检测 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficCamdetectionlogExtService {

    /**
     * 创建设备状态检测
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCamdetectionlog(@Valid TrafficCamdetectionlogSaveReqVO createReqVO);

}