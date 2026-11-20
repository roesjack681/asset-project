package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDevTollgateRectificationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 卡口质量评价 Service 接口
 *
 * @author 管理员
 */
public interface TrafficDevTollgateRectificationService {

    /**
     * 创建卡口质量评价
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDevTollgateRectification(@Valid TrafficDevTollgateRectificationSaveReqVO createReqVO);

    /**
     * 更新卡口质量评价
     *
     * @param updateReqVO 更新信息
     */
    void updateDevTollgateRectification(@Valid TrafficDevTollgateRectificationSaveReqVO updateReqVO);

    /**
     * 删除卡口质量评价
     *
     * @param id 编号
     */
    void deleteDevTollgateRectification(String id);

    /**
     * 获得卡口质量评价
     *
     * @param id 编号
     * @return 卡口质量评价
     */
    TrafficDevTollgateRectificationDO getDevTollgateRectification(String id);

    /**
     * 获得卡口质量评价分页
     *
     * @param pageReqVO 分页查询
     * @return 卡口质量评价分页
     */
    PageResult<TrafficDevTollgateRectificationDO> getDevTollgateRectificationPage(TrafficDevTollgateRectificationPageReqVO pageReqVO);

}