package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 公安设备监测结果历史 Service 接口
 *
 * @author yanxl
 */
public interface PoliceMonitorResultsHistoryService {

    /**
     * 创建公安设备监测结果历史
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsHistory(@Valid PoliceMonitorResultsHistorySaveReqVO createReqVO);

    /**
     * 更新公安设备监测结果历史
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsHistory(@Valid PoliceMonitorResultsHistorySaveReqVO updateReqVO);

    /**
     * 删除公安设备监测结果历史
     *
     * @param id 编号
     */
    void deleteMonitorResultsHistory(String id);

    /**
     * 获得公安设备监测结果历史
     *
     * @param id 编号
     * @return 公安设备监测结果历史
     */
    PoliceMonitorResultsHistoryDO getMonitorResultsHistory(String id);

    /**
     * 获得公安设备监测结果历史分页
     *
     * @param pageReqVO 分页查询
     * @return 公安设备监测结果历史分页
     */
    PageResult<PoliceMonitorResultsHistoryDO> getMonitorResultsHistoryPage(PoliceMonitorResultsHistoryPageReqVO pageReqVO);

}