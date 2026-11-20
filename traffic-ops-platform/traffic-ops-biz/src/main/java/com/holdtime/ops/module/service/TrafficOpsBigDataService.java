package com.holdtime.ops.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.TrafficOpsHealthRateDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMaintenanceRankDTO;
import com.holdtime.ops.module.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsBigDataService {


    TrafficOpsBigDataBaseRespVO getOpsBigDataBase(String jck,String dominion,String status);

    /**
     * 根据路口名称查询设备位置信息
     * @param intersectionName 路口名称
     * @return 设备位置信息
     */
    List<TrafficOpsPositionRespVO> queryTrafficOpsPositionList(String intersectionName);

    /**
     * 按故障类型分组统计
     * @param affiliatedUnit 所属单位
     * @param equLabel 设备标签
     * @return 分组统计类型
     */
    List<Map<String,Object>> getWxType(String affiliatedUnit, String equLabel);

    /**
     * 统计分析维修人员排名
     * @return 维修工作排名
     */
    List<TrafficOpsMaintenanceRankDTO> analysisMaintenanceRanking();

    /**
     * 分析近三年设备维修量对比
     * @param equType 设备类型
     * @return 近三年设备维修对比数据
     */
    Map<String, Collection<Integer>> analysisNearlyThreeYearsMaintenanceCnt(String equType);

    /**
     * 根据标签分析电警信息
     * @return 不同标签的电警数量
     */
    List<Map<String, Collection<Integer>>> analysisElectricPoliceByLabel();

    /**
     * 根据事件类型分析监控结果
     * @param equType 设备类型
     * @return 不同事件推送数量
     */
    List<Map<String, Collection<Integer>>> analysisMonitorResultByEventType(String equType);

    /**
     * 根据IP、方向以及事件类型分析设备监控结果
     * @param equType 设备类型
     * @return 设备监控结果
     */
    List<Map<String, Collection<Integer>>> analysisMonitorResultByKeyWord(String equType);

    /**
     * 查询维修点位信息
     * @return
     */
    List<TrafficOpsMaintainPointRespVO> queryMaintenancePoint();
    /**
     * 查询各电警类型数量
     * @return
     */
    List<TrafficOpsMaintainPointCountRespVO> queryMaintenancePointCount();

    /**
     * 查询各工单状态
     * @return
     */
    List<TrafficOpsMaintainPointCountRespVO> queryMaintenancePointStatusCount(String djType);

    /**
     * 统计运维工作白板
     * @return 当日工单数、派单率、完成率；待派单数、平均维修时长、待处理数
     */
    Map<String, Object> analysisMaintenanceDashboardCnt(String affiliatedUnit);

    /**
     * 工单完成情况
     * @return 当月、本年及全部工单数及完成率
     */
    Map<String, Object> analysisMaintenanceFinishSituation(String affiliatedUnit);

    /**
     * 根据标签分析电警监测异常数量
     * @param hour 近hour小时情况
     * @return 不同标签的检测异常数量
     */
    List<Map<String, Collection<Integer>>> analysisElectricPoliceMonitorByLabel(@Param("hour") Integer hour);

    /**
     * 分析集指考核设备
     * @param intervalDay 查询近多少天数据
     * @return 查询指定日期机智设备
     */
    List<TrafficOpsHealthRateDTO> analysisKhsbMonitorResult(@Param("intervalDay") Integer intervalDay);

    /**
     * 计算集指有效违法率
     * @return 有效违法率
     */
    BigDecimal calcVioValidSurveilAvgRate();

    /**
     * 按所属单位分析近一年设备维修量对比
     * @param affiliatedUnit 所属单位
     * @return 近一年设备维修量对比
     */
    Map<String, Integer> analysisNearlyOneYearsMaintenanceCnt(String affiliatedUnit);
}