package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorPingAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 电警通断检测管理 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PoliceMonitorPingAnalysisMapper extends BaseMapperX<PoliceMonitorPingAnalysisDO> {

    default PageResult<PoliceMonitorPingAnalysisDO> selectPage(PoliceMonitorPingAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorPingAnalysisDO>()
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getEquLable, reqVO.getEquLable())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoPingNormalCnt, reqVO.getAutoPingNormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoPingAbnormalCnt, reqVO.getAutoPingAbnormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoPingHealthRate, reqVO.getAutoPingHealthRate())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoMonitorCnt, reqVO.getAutoMonitorCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getPingNormalCnt, reqVO.getPingNormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getPingAbnormalCnt, reqVO.getPingAbnormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getPingHealthRate, reqVO.getPingHealthRate())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getMonitorCnt, reqVO.getMonitorCnt())
                .betweenIfPresent(PoliceMonitorPingAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorPingAnalysisDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorPingAnalysisDO::getId));
    }

}