package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficDevVioEquipmentDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 违法抓拍设备 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficDevVioEquipmentMapper extends BaseMapperX<TrafficDevVioEquipmentDO> {

    default PageResult<TrafficDevVioEquipmentDO> selectPage(TrafficDevVioEquipmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficDevVioEquipmentDO>()
                .eqIfPresent(TrafficDevVioEquipmentDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficDevVioEquipmentDO::getSblx, reqVO.getSblx())
                .eqIfPresent(TrafficDevVioEquipmentDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(TrafficDevVioEquipmentDO::getSbmc, reqVO.getSbmc())
                .eqIfPresent(TrafficDevVioEquipmentDO::getSzdd, reqVO.getSzdd())
                .betweenIfPresent(TrafficDevVioEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficDevVioEquipmentDO::getId));
    }

}