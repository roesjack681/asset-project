package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficLightsdetectionlogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信号灯检测信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficLightsdetectionlogService {

    /**
     * 创建信号灯检测信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLightsdetectionlog(@Valid TrafficLightsdetectionlogSaveReqVO createReqVO);

    /**
     * 更新信号灯检测信息
     *
     * @param updateReqVO 更新信息
     */
    void updateLightsdetectionlog(@Valid TrafficLightsdetectionlogSaveReqVO updateReqVO);

    /**
     * 删除信号灯检测信息
     *
     * @param id 编号
     */
    void deleteLightsdetectionlog(Long id);

    /**
     * 获得信号灯检测信息
     *
     * @param id 编号
     * @return 信号灯检测信息
     */
    TrafficLightsdetectionlogDO getLightsdetectionlog(Long id);

    /**
     * 获得信号灯检测信息分页
     *
     * @param pageReqVO 分页查询
     * @return 信号灯检测信息分页
     */
    PageResult<TrafficLightsdetectionlogDO> getLightsdetectionlogPage(TrafficLightsdetectionlogPageReqVO pageReqVO);

}