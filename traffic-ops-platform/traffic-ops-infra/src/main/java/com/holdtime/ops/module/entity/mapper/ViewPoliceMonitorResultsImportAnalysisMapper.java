package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewPoliceMonitorResultsImportAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewPoliceMonitorResultsImportAnalysisMapper extends BaseMapperX<ViewPoliceMonitorResultsImportAnalysisDO> {

    default PageResult<ViewPoliceMonitorResultsImportAnalysisDO> selectPage(ViewPoliceMonitorResultsImportAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewPoliceMonitorResultsImportAnalysisDO>()
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getEquLable, reqVO.getEquLable())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getVideoNormalCnt, reqVO.getVideoNormalCnt())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getVideoAbnormalCnt, reqVO.getVideoAbnormalCnt())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getVideoHealthRate, reqVO.getVideoHealthRate())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getPingNormalCnt, reqVO.getPingNormalCnt())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getPingAbnormalCnt, reqVO.getPingAbnormalCnt())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getPingHealthRate, reqVO.getPingHealthRate())
                .eqIfPresent(ViewPoliceMonitorResultsImportAnalysisDO::getMonitorSumCnt, reqVO.getMonitorSumCnt())
                .orderByDesc(ViewPoliceMonitorResultsImportAnalysisDO::getEquId));
    }

}