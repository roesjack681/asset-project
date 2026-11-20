package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备监测结果每日分析详情 Service 接口
 *
 * @author 管理员
 */
public interface TrafficMonitorResultsDailyDetailService {

    /**
     * 创建设备监测结果每日分析详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsDailyDetail(@Valid TrafficMonitorResultsDailyDetailSaveReqVO createReqVO);

    /**
     * 更新设备监测结果每日分析详情
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsDailyDetail(@Valid TrafficMonitorResultsDailyDetailSaveReqVO updateReqVO);

    /**
     * 删除设备监测结果每日分析详情
     *
     * @param id 编号
     */
    void deleteMonitorResultsDailyDetail(String id);

    /**
     * 获得设备监测结果每日分析详情
     *
     * @param id 编号
     * @return 设备监测结果每日分析详情
     */
    TrafficMonitorResultsDailyDetailDO getMonitorResultsDailyDetail(String id);

    /**
     * 获得设备监测结果每日分析详情分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果每日分析详情分页
     */
    PageResult<TrafficMonitorResultsDailyDetailDO> getMonitorResultsDailyDetailPage(TrafficMonitorResultsDailyDetailPageReqVO pageReqVO);

}