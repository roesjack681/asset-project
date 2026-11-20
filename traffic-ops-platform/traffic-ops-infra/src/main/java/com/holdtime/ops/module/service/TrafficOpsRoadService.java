package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsRoadDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 道路信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsRoadService {

    /**
     * 创建道路信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsRoad(@Valid TrafficOpsRoadSaveReqVO createReqVO);

    /**
     * 更新道路信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsRoad(@Valid TrafficOpsRoadSaveReqVO updateReqVO);

    /**
     * 删除道路信息
     *
     * @param id 编号
     */
    void deleteOpsRoad(String id);

    /**
     * 获得道路信息
     *
     * @param id 编号
     * @return 道路信息
     */
    TrafficOpsRoadDO getOpsRoad(String id);

    /**
     * 获得道路信息分页
     *
     * @param pageReqVO 分页查询
     * @return 道路信息分页
     */
    PageResult<TrafficOpsRoadDO> getOpsRoadPage(TrafficOpsRoadPageReqVO pageReqVO);

}