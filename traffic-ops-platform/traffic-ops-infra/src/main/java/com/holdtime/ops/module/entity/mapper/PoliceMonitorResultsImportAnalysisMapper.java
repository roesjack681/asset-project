package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 公安监测结果导入分析 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PoliceMonitorResultsImportAnalysisMapper extends BaseMapperX<PoliceMonitorResultsImportAnalysisDO> {

    default PageResult<PoliceMonitorResultsImportAnalysisDO> selectPage(PoliceMonitorResultsImportAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorResultsImportAnalysisDO>()
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getEquLable, reqVO.getEquLable())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getEventType, reqVO.getEventType())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getVideoNormalCnt, reqVO.getVideoNormalCnt())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getVideoAbnormalCnt, reqVO.getVideoAbnormalCnt())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getVideoHealthRate, reqVO.getVideoHealthRate())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getPingNormalCnt, reqVO.getPingNormalCnt())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getPingAbnormalCnt, reqVO.getPingAbnormalCnt())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getPingHealthRate, reqVO.getPingHealthRate())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getMonitorCnt, reqVO.getMonitorCnt())
                .betweenIfPresent(PoliceMonitorResultsImportAnalysisDO::getLastRepairTime, reqVO.getLastRepairTime())
                .betweenIfPresent(PoliceMonitorResultsImportAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorResultsImportAnalysisDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorResultsImportAnalysisDO::getId));
    }

}