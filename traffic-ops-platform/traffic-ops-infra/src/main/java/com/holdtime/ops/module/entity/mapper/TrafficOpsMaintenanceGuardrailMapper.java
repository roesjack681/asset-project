package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceGuardrailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏维护详细 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsMaintenanceGuardrailMapper extends BaseMapperX<TrafficOpsMaintenanceGuardrailDO> {

    default PageResult<TrafficOpsMaintenanceGuardrailDO> selectPage(TrafficOpsMaintenanceGuardrailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceGuardrailDO>()
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getGuardrailId, reqVO.getGuardrailId())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getMaintenanceType, reqVO.getMaintenanceType())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getChangeType, reqVO.getChangeType())
                .likeIfPresent(TrafficOpsMaintenanceGuardrailDO::getStartName, reqVO.getStartName())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getStartLng, reqVO.getStartLng())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getStartLat, reqVO.getStartLat())
                .likeIfPresent(TrafficOpsMaintenanceGuardrailDO::getEndName, reqVO.getEndName())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getEndLng, reqVO.getEndLng())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getEndLat, reqVO.getEndLat())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getIsGap, reqVO.getIsGap())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getModitfyBeProduct, reqVO.getModitfyBeProduct())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getModitfyAfProduct, reqVO.getModitfyAfProduct())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getModitfyBeImg, reqVO.getModitfyBeImg())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getModitfyAfImg, reqVO.getModitfyAfImg())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getModitfyDesc, reqVO.getModitfyDesc())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getClaimType, reqVO.getClaimType())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getIsHeadModitfy, reqVO.getIsHeadModitfy())
                .eqIfPresent(TrafficOpsMaintenanceGuardrailDO::getIsTailModitfy, reqVO.getIsTailModitfy())
                .betweenIfPresent(TrafficOpsMaintenanceGuardrailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceGuardrailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintenanceGuardrailDO::getId));
    }

}