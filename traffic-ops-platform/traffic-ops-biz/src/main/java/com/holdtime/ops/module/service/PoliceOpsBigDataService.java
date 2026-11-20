package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface PoliceOpsBigDataService {

    /**
     * 查询公安基础信息
     * @param equName 设备名称
     * @param dominion 管辖单位
     * @param status 状态
     * @return 返回大数据基础数据
     */
    PoliceOpsBigDataBaseRespVO getPoliceOpsBigDataBase(String equName, String dominion, String status);

    /**
     * 根据设备名称查询设备位置信息
     * @param equName 设备名称
     * @return 设备位置信息
     */
    List<PoliceOpsPositionRespVO> queryPoliceOpsPositionList(String equName);

    /**
     * 公安电警数量统计
     * @return 设备位置信息
     */
    Map<String, Object> analysisPoliceOpsElectricCnt();

    /**
     * 公安电警维修数量统计
     * @return 分析结果
     */
    List<Map<String, Object>> analysisPoliceOpsElectricMaintenanceCnt();

    /**
     * 公安电警近n小时不在线设备
     * @param hour 近n小时
     * @return 返回不在线设备数量
     */
    List<Map<String, Collection<Integer>>> analysisElectricPoliceMonitorByLabel(Integer hour);

    /**
     * 分析电警平均在线率
     * @return 平均在线率
     */
    Map<String, BigDecimal> analysisPoliceElectricPoliceOnOffAvgRate();
}