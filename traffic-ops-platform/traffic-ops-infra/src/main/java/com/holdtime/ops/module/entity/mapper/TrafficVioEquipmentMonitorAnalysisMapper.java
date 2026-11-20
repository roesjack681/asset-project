package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 集指设备检测分析 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficVioEquipmentMonitorAnalysisMapper extends BaseMapperX<TrafficVioEquipmentMonitorAnalysisDO> {

    default PageResult<TrafficVioEquipmentMonitorAnalysisDO> selectPage(TrafficVioEquipmentMonitorAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficVioEquipmentMonitorAnalysisDO>()
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getVideoNormalCnt, reqVO.getVideoNormalCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getVideoAbnormalCnt, reqVO.getVideoAbnormalCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getVideoHealthRate, reqVO.getVideoHealthRate())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getPingNormalCnt, reqVO.getPingNormalCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getPingAbnormalCnt, reqVO.getPingAbnormalCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getPingHealthRate, reqVO.getPingHealthRate())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getNoSurveilCnt, reqVO.getNoSurveilCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, reqVO.getValidSurveilCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getInvalidSurveilCnt, reqVO.getInvalidSurveilCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getRecognitionAccuracyRate, reqVO.getRecognitionAccuracyRate())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getMonitorCnt, reqVO.getMonitorCnt())
                .betweenIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficVioEquipmentMonitorAnalysisDO::getId));
    }

}