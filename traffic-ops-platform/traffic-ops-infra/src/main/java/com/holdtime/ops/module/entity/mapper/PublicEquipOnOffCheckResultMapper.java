package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicEquipOnOffCheckResultDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备通断测结果 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicEquipOnOffCheckResultMapper extends BaseMapperX<PublicEquipOnOffCheckResultDO> {

    default PageResult<PublicEquipOnOffCheckResultDO> selectPage(PublicEquipOnOffCheckResultPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicEquipOnOffCheckResultDO>()
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getEquType, reqVO.getEquType())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getIpAddr, reqVO.getIpAddr())
                .betweenIfPresent(PublicEquipOnOffCheckResultDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getPingStatus, reqVO.getPingStatus())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getVideoStatus, reqVO.getVideoStatus())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getResultImg, reqVO.getResultImg())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getIsRepairOrder, reqVO.getIsRepairOrder())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getCheckUserId, reqVO.getCheckUserId())
                .eqIfPresent(PublicEquipOnOffCheckResultDO::getCheckUserPersonnel, reqVO.getCheckUserPersonnel())
                .betweenIfPresent(PublicEquipOnOffCheckResultDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicEquipOnOffCheckResultDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicEquipOnOffCheckResultDO::getId));
    }

}