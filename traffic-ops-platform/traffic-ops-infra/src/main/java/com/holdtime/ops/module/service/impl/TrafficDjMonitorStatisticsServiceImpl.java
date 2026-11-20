package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficDjMonitorStatisticsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDjMonitorStatisticsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficDjMonitorStatisticsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 监测电警设备统计 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficDjMonitorStatisticsServiceImpl implements TrafficDjMonitorStatisticsService {

    @Resource
    private TrafficDjMonitorStatisticsMapper djMonitorStatisticsMapper;

    @Override
    public String createDjMonitorStatistics(TrafficDjMonitorStatisticsSaveReqVO createReqVO) {
        // 插入
        TrafficDjMonitorStatisticsDO djMonitorStatistics = BeanUtils.toBean(createReqVO, TrafficDjMonitorStatisticsDO.class);
        djMonitorStatisticsMapper.insert(djMonitorStatistics);
        // 返回
        return djMonitorStatistics.getId();
    }

    @Override
    public void updateDjMonitorStatistics(TrafficDjMonitorStatisticsSaveReqVO updateReqVO) {
        // 校验存在
        validateDjMonitorStatisticsExists(updateReqVO.getId());
        // 更新
        TrafficDjMonitorStatisticsDO updateObj = BeanUtils.toBean(updateReqVO, TrafficDjMonitorStatisticsDO.class);
        djMonitorStatisticsMapper.updateById(updateObj);
    }

    @Override
    public void deleteDjMonitorStatistics(String id) {
        // 校验存在
        validateDjMonitorStatisticsExists(id);
        // 删除
        djMonitorStatisticsMapper.deleteById(id);
    }

    private void validateDjMonitorStatisticsExists(String id) {
        if (djMonitorStatisticsMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_DJ_MONITOR_STATISTICS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficDjMonitorStatisticsDO getDjMonitorStatistics(String id) {
        return djMonitorStatisticsMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficDjMonitorStatisticsDO> getDjMonitorStatisticsPage(TrafficDjMonitorStatisticsPageReqVO pageReqVO) {
        return djMonitorStatisticsMapper.selectPage(pageReqVO);
    }

}