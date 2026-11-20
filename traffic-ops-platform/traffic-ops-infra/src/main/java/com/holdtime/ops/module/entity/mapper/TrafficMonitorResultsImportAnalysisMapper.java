package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备监测结果导入分析 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficMonitorResultsImportAnalysisMapper extends BaseMapperX<TrafficMonitorResultsImportAnalysisDO> {

    default PageResult<TrafficMonitorResultsImportAnalysisDO> selectPage(TrafficMonitorResultsImportAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficMonitorResultsImportAnalysisDO>()
                .betweenIfPresent(TrafficMonitorResultsImportAnalysisDO::getAnalysisDate, reqVO.getAnalysisDate())
                .eqIfPresent(TrafficMonitorResultsImportAnalysisDO::getNormalCnt, reqVO.getNormalCnt())
                .eqIfPresent(TrafficMonitorResultsImportAnalysisDO::getAbnormalCnt, reqVO.getAbnormalCnt())
                .eqIfPresent(TrafficMonitorResultsImportAnalysisDO::getHealthRate, reqVO.getHealthRate())
                .betweenIfPresent(TrafficMonitorResultsImportAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsImportAnalysisDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficMonitorResultsImportAnalysisDO::getId));
    }

}