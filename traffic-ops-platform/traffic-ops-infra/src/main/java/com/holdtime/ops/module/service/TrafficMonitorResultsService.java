package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备监测结果 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficMonitorResultsService {

    /**
     * 创建设备监测结果
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResults(@Valid TrafficMonitorResultsSaveReqVO createReqVO);

    /**
     * 更新设备监测结果
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResults(@Valid TrafficMonitorResultsSaveReqVO updateReqVO);

    /**
     * 删除设备监测结果
     *
     * @param id 编号
     */
    void deleteMonitorResults(String id);

    /**
     * 获得设备监测结果
     *
     * @param id 编号
     * @return 设备监测结果
     */
    TrafficMonitorResultsDO getMonitorResults(String id);

    /**
     * 获得设备监测结果分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果分页
     */
    PageResult<TrafficMonitorResultsDO> getMonitorResultsPage(TrafficMonitorResultsPageReqVO pageReqVO);

}