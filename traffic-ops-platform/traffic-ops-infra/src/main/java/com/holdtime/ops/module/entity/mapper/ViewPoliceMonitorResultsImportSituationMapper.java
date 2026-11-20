package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewPoliceMonitorResultsImportSituationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewPoliceMonitorResultsImportSituationMapper extends BaseMapperX<ViewPoliceMonitorResultsImportSituationDO> {

    default PageResult<ViewPoliceMonitorResultsImportSituationDO> selectPage(ViewPoliceMonitorResultsImportSituationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewPoliceMonitorResultsImportSituationDO>()
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getLatestOccurrenceDay, reqVO.getLatestOccurrenceDay())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getPreviousOccurrenceDay, reqVO.getPreviousOccurrenceDay())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getLatestPingStatus, reqVO.getLatestPingStatus())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getPreviousPingStatus, reqVO.getPreviousPingStatus())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getFinalPingStatus, reqVO.getFinalPingStatus())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getLatestVideoStatus, reqVO.getLatestVideoStatus())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getPreviousVideoStatus, reqVO.getPreviousVideoStatus())
                .eqIfPresent(ViewPoliceMonitorResultsImportSituationDO::getFinalVideoStatus, reqVO.getFinalVideoStatus())
                .orderByDesc(ViewPoliceMonitorResultsImportSituationDO::getEquId));
    }

}