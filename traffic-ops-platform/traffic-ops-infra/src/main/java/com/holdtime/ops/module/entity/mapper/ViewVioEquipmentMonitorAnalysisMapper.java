package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewVioEquipmentMonitorAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 违法抓拍设备监控分析 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewVioEquipmentMonitorAnalysisMapper extends BaseMapperX<ViewVioEquipmentMonitorAnalysisDO> {

    default PageResult<ViewVioEquipmentMonitorAnalysisDO> selectPage(ViewVioEquipmentMonitorAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewVioEquipmentMonitorAnalysisDO>()
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getPingNormalCnt, reqVO.getPingNormalCnt())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getPingAbnormalCnt, reqVO.getPingAbnormalCnt())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getPingHealthRate, reqVO.getPingHealthRate())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getVideoNormalCnt, reqVO.getVideoNormalCnt())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getVideoAbnormalCnt, reqVO.getVideoAbnormalCnt())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getVideoHealthRate, reqVO.getVideoHealthRate())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getNoSurveilCnt, reqVO.getNoSurveilCnt())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, reqVO.getValidSurveilCnt())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getInvalidSurveilCnt, reqVO.getInvalidSurveilCnt())
                .eqIfPresent(ViewVioEquipmentMonitorAnalysisDO::getRecognitionAccuracyRate, reqVO.getRecognitionAccuracyRate())
                .betweenIfPresent(ViewVioEquipmentMonitorAnalysisDO::getRecognitionAccuracyPickDate, reqVO.getRecognitionAccuracyPickDate())
                .orderByDesc(ViewVioEquipmentMonitorAnalysisDO::getEquId));
    }

}