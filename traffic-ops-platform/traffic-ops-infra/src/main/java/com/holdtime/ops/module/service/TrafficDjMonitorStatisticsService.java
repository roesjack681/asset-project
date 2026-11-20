package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDjMonitorStatisticsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 监测电警设备统计 Service 接口
 *
 * @author 管理员
 */
public interface TrafficDjMonitorStatisticsService {

    /**
     * 创建监测电警设备统计
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDjMonitorStatistics(@Valid TrafficDjMonitorStatisticsSaveReqVO createReqVO);

    /**
     * 更新监测电警设备统计
     *
     * @param updateReqVO 更新信息
     */
    void updateDjMonitorStatistics(@Valid TrafficDjMonitorStatisticsSaveReqVO updateReqVO);

    /**
     * 删除监测电警设备统计
     *
     * @param id 编号
     */
    void deleteDjMonitorStatistics(String id);

    /**
     * 获得监测电警设备统计
     *
     * @param id 编号
     * @return 监测电警设备统计
     */
    TrafficDjMonitorStatisticsDO getDjMonitorStatistics(String id);

    /**
     * 获得监测电警设备统计分页
     *
     * @param pageReqVO 分页查询
     * @return 监测电警设备统计分页
     */
    PageResult<TrafficDjMonitorStatisticsDO> getDjMonitorStatisticsPage(TrafficDjMonitorStatisticsPageReqVO pageReqVO);

}