package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 集指设备检测记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficVioEquipmentMonitorDetailMapper extends BaseMapperX<TrafficVioEquipmentMonitorDetailDO> {

    default PageResult<TrafficVioEquipmentMonitorDetailDO> selectPage(TrafficVioEquipmentMonitorDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficVioEquipmentMonitorDetailDO>()
                .betweenIfPresent(TrafficVioEquipmentMonitorDetailDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getPingStatus, reqVO.getPingStatus())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getVideoStatus, reqVO.getVideoStatus())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getNoSurveilCnt, reqVO.getNoSurveilCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getValidSurveilCnt, reqVO.getValidSurveilCnt())
                .eqIfPresent(TrafficVioEquipmentMonitorDetailDO::getInvalidSurveilCnt, reqVO.getInvalidSurveilCnt())
                .betweenIfPresent(TrafficVioEquipmentMonitorDetailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficVioEquipmentMonitorDetailDO::getId));
    }

}