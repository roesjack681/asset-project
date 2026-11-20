package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionDayPlanDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信控交叉口日方案详细信息 Service 接口
 *
 * @author yangli
 */
public interface TrafficTslJunctionDayPlanDetailService {

    /**
     * 创建信控交叉口日方案详细信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTslJunctionDayPlanDetail(@Valid TrafficTslJunctionDayPlanDetailSaveReqVO createReqVO);

    /**
     * 更新信控交叉口日方案详细信息
     *
     * @param updateReqVO 更新信息
     */
    void updateTslJunctionDayPlanDetail(@Valid TrafficTslJunctionDayPlanDetailSaveReqVO updateReqVO);

    /**
     * 删除信控交叉口日方案详细信息
     *
     * @param id 编号
     */
    void deleteTslJunctionDayPlanDetail(String id);

    /**
     * 获得信控交叉口日方案详细信息
     *
     * @param id 编号
     * @return 信控交叉口日方案详细信息
     */
    TrafficTslJunctionDayPlanDetailDO getTslJunctionDayPlanDetail(String id);

    /**
     * 获得信控交叉口日方案详细信息分页
     *
     * @param pageReqVO 分页查询
     * @return 信控交叉口日方案详细信息分页
     */
    PageResult<TrafficTslJunctionDayPlanDetailDO> getTslJunctionDayPlanDetailPage(TrafficTslJunctionDayPlanDetailPageReqVO pageReqVO);

}