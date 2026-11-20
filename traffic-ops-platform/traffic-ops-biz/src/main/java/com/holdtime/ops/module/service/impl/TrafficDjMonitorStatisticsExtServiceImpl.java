package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.TrafficDjMonitorStatisticsDO;
import com.holdtime.ops.module.entity.mapper.TrafficDjMonitorStatisticsMapper;
import com.holdtime.ops.module.model.vo.TrafficDjMonitorStatisticsExtSaveReqVO;
import com.holdtime.ops.module.service.TrafficDjMonitorStatisticsExtService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 监测电警设备统计 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficDjMonitorStatisticsExtServiceImpl implements TrafficDjMonitorStatisticsExtService {

    @Resource
    private TrafficDjMonitorStatisticsMapper djMonitorStatisticsMapper;

    @Override
    public String syncElectricPoliceMonitorStatistics(@Valid TrafficDjMonitorStatisticsExtSaveReqVO createReqVO) {
        // 插入
        TrafficDjMonitorStatisticsDO djMonitorStatistics = BeanUtils.toBean(createReqVO, TrafficDjMonitorStatisticsDO.class);
        djMonitorStatistics.setOccurrenceTime(DateUtil.toLocalDateTime(createReqVO.getOccurrenceTime()));
        djMonitorStatisticsMapper.insert(djMonitorStatistics);
        // 返回
        return djMonitorStatistics.getId();
    }

}