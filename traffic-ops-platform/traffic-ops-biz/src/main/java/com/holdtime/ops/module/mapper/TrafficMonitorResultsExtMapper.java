package com.holdtime.ops.module.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * 维保自定义Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface TrafficMonitorResultsExtMapper {
    /**
     * 删除交警检测数据
     * @param occurrenceTime
     * @return
     */
    @Delete("DELETE from traffic_monitor_results where equ_type = '2' and occurrence_time < #{occurrenceTime}")
    int deleteTrafficMonitorResults(Date occurrenceTime);

    /**
     * 清楚全部数据重新分析
     * @return
     */
    @Delete("DELETE from traffic_vio_equipment_monitor_analysis")
    int deleteTrafficVioEquipmentMonitorAnalysis();


}