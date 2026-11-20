package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewPoliceMonitorPingDetailAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author yanxl
 */
@Mapper
public interface ViewPoliceMonitorPingDetailAnalysisMapper extends BaseMapperX<ViewPoliceMonitorPingDetailAnalysisDO> {

    default PageResult<ViewPoliceMonitorPingDetailAnalysisDO> selectPage(ViewPoliceMonitorPingDetailAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewPoliceMonitorPingDetailAnalysisDO>()
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getAutoNormalCnt, reqVO.getAutoNormalCnt())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getAutoAbnormalCnt, reqVO.getAutoAbnormalCnt())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getAutoMonitorCnt, reqVO.getAutoMonitorCnt())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getAutoHealthRate, reqVO.getAutoHealthRate())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getNormalCnt, reqVO.getNormalCnt())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getAbnormalCnt, reqVO.getAbnormalCnt())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getMonitorCnt, reqVO.getMonitorCnt())
                .eqIfPresent(ViewPoliceMonitorPingDetailAnalysisDO::getHealthRate, reqVO.getHealthRate())
                .orderByDesc(ViewPoliceMonitorPingDetailAnalysisDO::getEquId));
    }

}