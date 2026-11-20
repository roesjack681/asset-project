package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailAuxiliaryDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏辅助设备 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsGuardrailAuxiliaryMapper extends BaseMapperX<TrafficOpsGuardrailAuxiliaryDO> {

    default PageResult<TrafficOpsGuardrailAuxiliaryDO> selectPage(TrafficOpsGuardrailAuxiliaryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsGuardrailAuxiliaryDO>()
                .eqIfPresent(TrafficOpsGuardrailAuxiliaryDO::getGuardrailId, reqVO.getGuardrailId())
                .eqIfPresent(TrafficOpsGuardrailAuxiliaryDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsGuardrailAuxiliaryDO::getManceGuardrailId, reqVO.getManceGuardrailId())
                .likeIfPresent(TrafficOpsGuardrailAuxiliaryDO::getAuxiliaryName, reqVO.getAuxiliaryName())
                .eqIfPresent(TrafficOpsGuardrailAuxiliaryDO::getAuxiliaryNum, reqVO.getAuxiliaryNum())
                .eqIfPresent(TrafficOpsGuardrailAuxiliaryDO::getAuxiliaryImg, reqVO.getAuxiliaryImg())
                .eqIfPresent(TrafficOpsGuardrailAuxiliaryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TrafficOpsGuardrailAuxiliaryDO::getInstallTime, reqVO.getInstallTime())
                .betweenIfPresent(TrafficOpsGuardrailAuxiliaryDO::getDemolishTime, reqVO.getDemolishTime())
                .betweenIfPresent(TrafficOpsGuardrailAuxiliaryDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsGuardrailAuxiliaryDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsGuardrailAuxiliaryDO::getId));
    }

}