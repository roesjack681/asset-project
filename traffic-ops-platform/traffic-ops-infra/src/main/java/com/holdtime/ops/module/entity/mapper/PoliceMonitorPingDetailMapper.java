package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorPingDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 公安监测通达详情 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PoliceMonitorPingDetailMapper extends BaseMapperX<PoliceMonitorPingDetailDO> {

    default PageResult<PoliceMonitorPingDetailDO> selectPage(PoliceMonitorPingDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorPingDetailDO>()
                .eqIfPresent(PoliceMonitorPingDetailDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorPingDetailDO::getIpAddr, reqVO.getIpAddr())
                .likeIfPresent(PoliceMonitorPingDetailDO::getEquLable, reqVO.getEquLable())
                .betweenIfPresent(PoliceMonitorPingDetailDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorPingDetailDO::getPingStatus, reqVO.getPingStatus())
                .eqIfPresent(PoliceMonitorPingDetailDO::getPingCnt, reqVO.getPingCnt())
                .eqIfPresent(PoliceMonitorPingDetailDO::getLossRate, reqVO.getLossRate())
                .eqIfPresent(PoliceMonitorPingDetailDO::getSource, reqVO.getSource())
                .betweenIfPresent(PoliceMonitorPingDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorPingDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorPingDetailDO::getId));
    }

}