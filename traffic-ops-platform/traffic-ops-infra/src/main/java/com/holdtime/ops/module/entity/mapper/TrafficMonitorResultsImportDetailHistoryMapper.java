package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailHistoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备监测结果导入信息历史 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficMonitorResultsImportDetailHistoryMapper extends BaseMapperX<TrafficMonitorResultsImportDetailHistoryDO> {

    default PageResult<TrafficMonitorResultsImportDetailHistoryDO> selectPage(TrafficMonitorResultsImportDetailHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficMonitorResultsImportDetailHistoryDO>()
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getDetailId, reqVO.getDetailId())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getIpAddr, reqVO.getIpAddr())
                .likeIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getMemberBh, reqVO.getMemberBh())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getDirection, reqVO.getDirection())
                .betweenIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getMonitorSituation, reqVO.getMonitorSituation())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getEquSituation, reqVO.getEquSituation())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getEquDesc, reqVO.getEquDesc())
                .eqIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getEvidenceFileId, reqVO.getEvidenceFileId())
                .betweenIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsImportDetailHistoryDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficMonitorResultsImportDetailHistoryDO::getId));
    }

}