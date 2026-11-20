package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslRegionInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信控区域信息 Service 接口
 *
 * @author yangli
 */
public interface TrafficTslRegionInfoService {

    /**
     * 创建信控区域信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTslRegionInfo(@Valid TrafficTslRegionInfoSaveReqVO createReqVO);

    /**
     * 更新信控区域信息
     *
     * @param updateReqVO 更新信息
     */
    void updateTslRegionInfo(@Valid TrafficTslRegionInfoSaveReqVO updateReqVO);

    /**
     * 删除信控区域信息
     *
     * @param id 编号
     */
    void deleteTslRegionInfo(String id);

    /**
     * 获得信控区域信息
     *
     * @param id 编号
     * @return 信控区域信息
     */
    TrafficTslRegionInfoDO getTslRegionInfo(String id);

    /**
     * 获得信控区域信息分页
     *
     * @param pageReqVO 分页查询
     * @return 信控区域信息分页
     */
    PageResult<TrafficTslRegionInfoDO> getTslRegionInfoPage(TrafficTslRegionInfoPageReqVO pageReqVO);

}