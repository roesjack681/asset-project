package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficCrossingDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 路口信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficCrossingService {

    /**
     * 创建路口信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCrossing(@Valid TrafficCrossingSaveReqVO createReqVO);

    /**
     * 更新路口信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCrossing(@Valid TrafficCrossingSaveReqVO updateReqVO);

    /**
     * 删除路口信息
     *
     * @param id 编号
     */
    void deleteCrossing(Long id);

    /**
     * 获得路口信息
     *
     * @param id 编号
     * @return 路口信息
     */
    TrafficCrossingDO getCrossing(Long id);

    /**
     * 获得路口信息分页
     *
     * @param pageReqVO 分页查询
     * @return 路口信息分页
     */
    PageResult<TrafficCrossingDO> getCrossingPage(TrafficCrossingPageReqVO pageReqVO);

}