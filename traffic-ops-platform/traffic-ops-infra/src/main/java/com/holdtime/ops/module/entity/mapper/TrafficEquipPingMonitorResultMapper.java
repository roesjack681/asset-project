package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 重点考核设备通断检测 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficEquipPingMonitorResultMapper extends BaseMapperX<TrafficEquipPingMonitorResultDO> {

    default PageResult<TrafficEquipPingMonitorResultDO> selectPage(TrafficEquipPingMonitorResultPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficEquipPingMonitorResultDO>()
                .eqIfPresent(TrafficEquipPingMonitorResultDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficEquipPingMonitorResultDO::getIpAddr, reqVO.getIpAddr())
                .betweenIfPresent(TrafficEquipPingMonitorResultDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficEquipPingMonitorResultDO::getPingStatus, reqVO.getPingStatus())
                .eqIfPresent(TrafficEquipPingMonitorResultDO::getPingCnt, reqVO.getPingCnt())
                .eqIfPresent(TrafficEquipPingMonitorResultDO::getLossRate, reqVO.getLossRate())
                .eqIfPresent(TrafficEquipPingMonitorResultDO::getSource, reqVO.getSource())
                .eqIfPresent(TrafficEquipPingMonitorResultDO::getExecBatchNum, reqVO.getExecBatchNum())
                .betweenIfPresent(TrafficEquipPingMonitorResultDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficEquipPingMonitorResultDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficEquipPingMonitorResultDO::getId));
    }

}