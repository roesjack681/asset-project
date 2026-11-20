package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备监测结果历史 Service 接口
 *
 * @author 管理员
 */
public interface TrafficMonitorResultsHistoryService {

    /**
     * 创建设备监测结果历史
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsHistory(@Valid TrafficMonitorResultsHistorySaveReqVO createReqVO);

    /**
     * 更新设备监测结果历史
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsHistory(@Valid TrafficMonitorResultsHistorySaveReqVO updateReqVO);

    /**
     * 删除设备监测结果历史
     *
     * @param id 编号
     */
    void deleteMonitorResultsHistory(String id);

    /**
     * 获得设备监测结果历史
     *
     * @param id 编号
     * @return 设备监测结果历史
     */
    TrafficMonitorResultsHistoryDO getMonitorResultsHistory(String id);

    /**
     * 获得设备监测结果历史分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果历史分页
     */
    PageResult<TrafficMonitorResultsHistoryDO> getMonitorResultsHistoryPage(TrafficMonitorResultsHistoryPageReqVO pageReqVO);

}