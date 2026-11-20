package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewVioSurveilAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewVioSurveilAnalysisMapper extends BaseMapperX<ViewVioSurveilAnalysisDO> {

    default PageResult<ViewVioSurveilAnalysisDO> selectPage(ViewVioSurveilAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewVioSurveilAnalysisDO>()
                .eqIfPresent(ViewVioSurveilAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getDjType, reqVO.getDjType())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getEquLable, reqVO.getEquLable())
                .betweenIfPresent(ViewVioSurveilAnalysisDO::getOccurrenceDay, reqVO.getOccurrenceDay())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getNormalCnt, reqVO.getNormalCnt())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getAbnormalCnt, reqVO.getAbnormalCnt())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getMonitorSumDays, reqVO.getMonitorSumDays())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getMonitorSumCnt, reqVO.getMonitorSumCnt())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getHealthRate, reqVO.getHealthRate())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ViewVioSurveilAnalysisDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getResultImg, reqVO.getResultImg())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getMonitorSituation, reqVO.getMonitorSituation())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getNoSurveilCnt, reqVO.getNoSurveilCnt())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getValidSurveilCnt, reqVO.getValidSurveilCnt())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getInvalidSurveilCnt, reqVO.getInvalidSurveilCnt())
                .eqIfPresent(ViewVioSurveilAnalysisDO::getRecognitionAccuracyRate, reqVO.getRecognitionAccuracyRate())
                .betweenIfPresent(ViewVioSurveilAnalysisDO::getRecognitionAccuracyPickDate, reqVO.getRecognitionAccuracyPickDate())
                .orderByDesc(ViewVioSurveilAnalysisDO::getSbbh)
                .orderByDesc(ViewVioSurveilAnalysisDO::getOccurrenceDay));
    }

}