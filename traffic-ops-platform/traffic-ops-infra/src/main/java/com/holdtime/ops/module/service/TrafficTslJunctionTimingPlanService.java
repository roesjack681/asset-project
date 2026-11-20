package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionTimingPlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信控交叉口配时方案信息 Service 接口
 *
 * @author yangli
 */
public interface TrafficTslJunctionTimingPlanService {

    /**
     * 创建信控交叉口配时方案信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTslJunctionTimingPlan(@Valid TrafficTslJunctionTimingPlanSaveReqVO createReqVO);

    /**
     * 更新信控交叉口配时方案信息
     *
     * @param updateReqVO 更新信息
     */
    void updateTslJunctionTimingPlan(@Valid TrafficTslJunctionTimingPlanSaveReqVO updateReqVO);

    /**
     * 删除信控交叉口配时方案信息
     *
     * @param id 编号
     */
    void deleteTslJunctionTimingPlan(String id);

    /**
     * 获得信控交叉口配时方案信息
     *
     * @param id 编号
     * @return 信控交叉口配时方案信息
     */
    TrafficTslJunctionTimingPlanDO getTslJunctionTimingPlan(String id);

    /**
     * 获得信控交叉口配时方案信息分页
     *
     * @param pageReqVO 分页查询
     * @return 信控交叉口配时方案信息分页
     */
    PageResult<TrafficTslJunctionTimingPlanDO> getTslJunctionTimingPlanPage(TrafficTslJunctionTimingPlanPageReqVO pageReqVO);

}