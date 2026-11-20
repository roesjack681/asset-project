package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsPoliceStationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 警用装备采集站 Mapper
 *
 * @author 杨丽
 */
@Mapper
public interface TrafficOpsPoliceStationMapper extends BaseMapperX<TrafficOpsPoliceStationDO> {

    default PageResult<TrafficOpsPoliceStationDO> selectPage(TrafficOpsPoliceStationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsPoliceStationDO>()
                .eqIfPresent(TrafficOpsPoliceStationDO::getUseDeptBh, reqVO.getUseDeptBh())
                .eqIfPresent(TrafficOpsPoliceStationDO::getUseDept, reqVO.getUseDept())
                .eqIfPresent(TrafficOpsPoliceStationDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficOpsPoliceStationDO::getAddress, reqVO.getAddress())
                .eqIfPresent(TrafficOpsPoliceStationDO::getEquCode, reqVO.getEquCode())
                .eqIfPresent(TrafficOpsPoliceStationDO::getManufacturer, reqVO.getManufacturer())
                .likeIfPresent(TrafficOpsPoliceStationDO::getPcName, reqVO.getPcName())
                .eqIfPresent(TrafficOpsPoliceStationDO::getHardDisk, reqVO.getHardDisk())
                .eqIfPresent(TrafficOpsPoliceStationDO::getProcessor, reqVO.getProcessor())
                .eqIfPresent(TrafficOpsPoliceStationDO::getOperatingSystem, reqVO.getOperatingSystem())
                .eqIfPresent(TrafficOpsPoliceStationDO::getMac, reqVO.getMac())
                .eqIfPresent(TrafficOpsPoliceStationDO::getMemory, reqVO.getMemory())
                .eqIfPresent(TrafficOpsPoliceStationDO::getSoftVersion, reqVO.getSoftVersion())
                .betweenIfPresent(TrafficOpsPoliceStationDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsPoliceStationDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsPoliceStationDO::getId));
    }

}