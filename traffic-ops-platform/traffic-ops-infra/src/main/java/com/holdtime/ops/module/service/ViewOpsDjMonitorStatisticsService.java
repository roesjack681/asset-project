package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsDjMonitorStatisticsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 电警监控信息 Service 接口
 *
 * @author 管理员
 */
public interface ViewOpsDjMonitorStatisticsService {

    /**
     * 创建电警监控信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createViewOpsDjMonitorStatistics(@Valid ViewOpsDjMonitorStatisticsSaveReqVO createReqVO);

    /**
     * 更新电警监控信息
     *
     * @param updateReqVO 更新信息
     */
    void updateViewOpsDjMonitorStatistics(@Valid ViewOpsDjMonitorStatisticsSaveReqVO updateReqVO);

    /**
     * 删除电警监控信息
     *
     * @param id 编号
     */
    void deleteViewOpsDjMonitorStatistics(String id);

    /**
     * 获得电警监控信息
     *
     * @param id 编号
     * @return 电警监控信息
     */
    ViewOpsDjMonitorStatisticsDO getViewOpsDjMonitorStatistics(String id);

    /**
     * 获得电警监控信息分页
     *
     * @param pageReqVO 分页查询
     * @return 电警监控信息分页
     */
    PageResult<ViewOpsDjMonitorStatisticsDO> getViewOpsDjMonitorStatisticsPage(ViewOpsDjMonitorStatisticsPageReqVO pageReqVO);

}