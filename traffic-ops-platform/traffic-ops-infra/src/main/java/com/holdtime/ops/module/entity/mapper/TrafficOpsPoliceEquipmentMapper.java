package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsPoliceEquipmentDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 警用装备资产信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsPoliceEquipmentMapper extends BaseMapperX<TrafficOpsPoliceEquipmentDO> {

    default PageResult<TrafficOpsPoliceEquipmentDO> selectPage(TrafficOpsPoliceEquipmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO>()
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getEncryptionCard, reqVO.getEncryptionCard())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getPsn, reqVO.getPsn())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getBrand, reqVO.getBrand())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getModel, reqVO.getModel())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getBattery, reqVO.getBattery())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getVersion, reqVO.getVersion())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordBh, reqVO.getRecordBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecorder, reqVO.getRecorder())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordDeptBh, reqVO.getRecordDeptBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordDept, reqVO.getRecordDept())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDeptBh, reqVO.getUseDeptBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDept, reqVO.getUseDept())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUserBh, reqVO.getUserBh())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getUserName, reqVO.getUserName())
                .betweenIfPresent(TrafficOpsPoliceEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsPoliceEquipmentDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsPoliceEquipmentDO::getId));
    }

}