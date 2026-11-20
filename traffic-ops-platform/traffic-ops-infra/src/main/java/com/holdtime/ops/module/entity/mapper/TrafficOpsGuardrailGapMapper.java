package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailGapDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏开口信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsGuardrailGapMapper extends BaseMapperX<TrafficOpsGuardrailGapDO> {

    default PageResult<TrafficOpsGuardrailGapDO> selectPage(TrafficOpsGuardrailGapPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsGuardrailGapDO>()
                .eqIfPresent(TrafficOpsGuardrailGapDO::getGuardrailId, reqVO.getGuardrailId())
                .eqIfPresent(TrafficOpsGuardrailGapDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsGuardrailGapDO::getManceGuardrailId, reqVO.getManceGuardrailId())
                .likeIfPresent(TrafficOpsGuardrailGapDO::getGapName, reqVO.getGapName())
                .eqIfPresent(TrafficOpsGuardrailGapDO::getGapType, reqVO.getGapType())
                .eqIfPresent(TrafficOpsGuardrailGapDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsGuardrailGapDO::getLat, reqVO.getLat())
                .betweenIfPresent(TrafficOpsGuardrailGapDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsGuardrailGapDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsGuardrailGapDO::getId));
    }

}