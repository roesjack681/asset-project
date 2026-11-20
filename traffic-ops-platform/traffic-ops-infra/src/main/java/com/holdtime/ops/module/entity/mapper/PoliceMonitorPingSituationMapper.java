package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorPingSituationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 公安通断监测近两日状态 Mapper
 *
 * @author yanxl
 */
@Mapper
public interface PoliceMonitorPingSituationMapper extends BaseMapperX<PoliceMonitorPingSituationDO> {

    default PageResult<PoliceMonitorPingSituationDO> selectPage(PoliceMonitorPingSituationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorPingSituationDO>()
                .eqIfPresent(PoliceMonitorPingSituationDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorPingSituationDO::getIpAddr, reqVO.getIpAddr())
                .betweenIfPresent(PoliceMonitorPingSituationDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorPingSituationDO::getPingStatus, reqVO.getPingStatus())
                .betweenIfPresent(PoliceMonitorPingSituationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PoliceMonitorPingSituationDO::getId));
    }

}