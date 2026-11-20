package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsAccessoryEquipmentDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 杆件附属设备 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsAccessoryEquipmentMapper extends BaseMapperX<TrafficOpsAccessoryEquipmentDO> {

    default PageResult<TrafficOpsAccessoryEquipmentDO> selectPage(TrafficOpsAccessoryEquipmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsAccessoryEquipmentDO>()
                .eqIfPresent(TrafficOpsAccessoryEquipmentDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(TrafficOpsAccessoryEquipmentDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsAccessoryEquipmentDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsAccessoryEquipmentDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(TrafficOpsAccessoryEquipmentDO::getEquNum, reqVO.getEquNum())
                .eqIfPresent(TrafficOpsAccessoryEquipmentDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TrafficOpsAccessoryEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsAccessoryEquipmentDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsAccessoryEquipmentDO::getId));
    }

}