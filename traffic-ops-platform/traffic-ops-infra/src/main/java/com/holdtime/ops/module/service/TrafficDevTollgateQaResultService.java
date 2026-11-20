package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDevTollgateQaResultDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 卡口质量检测结果 Service 接口
 *
 * @author 管理员
 */
public interface TrafficDevTollgateQaResultService {

    /**
     * 创建卡口质量检测结果
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDevTollgateQaResult(@Valid TrafficDevTollgateQaResultSaveReqVO createReqVO);

    /**
     * 更新卡口质量检测结果
     *
     * @param updateReqVO 更新信息
     */
    void updateDevTollgateQaResult(@Valid TrafficDevTollgateQaResultSaveReqVO updateReqVO);

    /**
     * 删除卡口质量检测结果
     *
     * @param id 编号
     */
    void deleteDevTollgateQaResult(String id);

    /**
     * 获得卡口质量检测结果
     *
     * @param id 编号
     * @return 卡口质量检测结果
     */
    TrafficDevTollgateQaResultDO getDevTollgateQaResult(String id);

    /**
     * 获得卡口质量检测结果分页
     *
     * @param pageReqVO 分页查询
     * @return 卡口质量检测结果分页
     */
    PageResult<TrafficDevTollgateQaResultDO> getDevTollgateQaResultPage(TrafficDevTollgateQaResultPageReqVO pageReqVO);

}