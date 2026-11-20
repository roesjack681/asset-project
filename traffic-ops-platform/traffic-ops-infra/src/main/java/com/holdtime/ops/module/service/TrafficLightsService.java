package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficLightsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信号灯信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficLightsService {

    /**
     * 创建信号灯信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLights(@Valid TrafficLightsSaveReqVO createReqVO);

    /**
     * 更新信号灯信息
     *
     * @param updateReqVO 更新信息
     */
    void updateLights(@Valid TrafficLightsSaveReqVO updateReqVO);

    /**
     * 删除信号灯信息
     *
     * @param id 编号
     */
    void deleteLights(Long id);

    /**
     * 获得信号灯信息
     *
     * @param id 编号
     * @return 信号灯信息
     */
    TrafficLightsDO getLights(Long id);

    /**
     * 获得信号灯信息分页
     *
     * @param pageReqVO 分页查询
     * @return 信号灯信息分页
     */
    PageResult<TrafficLightsDO> getLightsPage(TrafficLightsPageReqVO pageReqVO);

}