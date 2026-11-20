package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailDO;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailHistoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备监测结果导入信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficMonitorResultsImportDetailMapper extends BaseMapperX<TrafficMonitorResultsImportDetailDO> {

    default PageResult<TrafficMonitorResultsImportDetailDO> selectPage(TrafficMonitorResultsImportDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficMonitorResultsImportDetailDO>()
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getIpAddr, reqVO.getIpAddr())
                .likeIfPresent(TrafficMonitorResultsImportDetailDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficMonitorResultsImportDetailDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getMemberBh, reqVO.getMemberBh())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getDirection, reqVO.getDirection())
                .betweenIfPresent(TrafficMonitorResultsImportDetailDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getMonitorSituation, reqVO.getMonitorSituation())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getEquSituation, reqVO.getEquSituation())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getEquDesc, reqVO.getEquDesc())
                .eqIfPresent(TrafficMonitorResultsImportDetailDO::getEvidenceFileId, reqVO.getEvidenceFileId())
                .betweenIfPresent(TrafficMonitorResultsImportDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsImportDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficMonitorResultsImportDetailDO::getId));
    }

}