package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备监测结果导入信息历史 Service 接口
 *
 * @author 管理员
 */
public interface TrafficMonitorResultsImportDetailHistoryService {

    /**
     * 创建设备监测结果导入信息历史
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsImportDetailHistory(@Valid TrafficMonitorResultsImportDetailHistorySaveReqVO createReqVO);

    /**
     * 更新设备监测结果导入信息历史
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsImportDetailHistory(@Valid TrafficMonitorResultsImportDetailHistorySaveReqVO updateReqVO);

    /**
     * 删除设备监测结果导入信息历史
     *
     * @param id 编号
     */
    void deleteMonitorResultsImportDetailHistory(String id);

    /**
     * 获得设备监测结果导入信息历史
     *
     * @param id 编号
     * @return 设备监测结果导入信息历史
     */
    TrafficMonitorResultsImportDetailHistoryDO getMonitorResultsImportDetailHistory(String id);

    /**
     * 获得设备监测结果导入信息历史分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果导入信息历史分页
     */
    PageResult<TrafficMonitorResultsImportDetailHistoryDO> getMonitorResultsImportDetailHistoryPage(TrafficMonitorResultsImportDetailHistoryPageReqVO pageReqVO);

}