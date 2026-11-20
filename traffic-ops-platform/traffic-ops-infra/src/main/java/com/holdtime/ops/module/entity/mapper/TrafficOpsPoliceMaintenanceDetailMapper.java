package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsPoliceMaintenanceDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 警用装备报修详情信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsPoliceMaintenanceDetailMapper extends BaseMapperX<TrafficOpsPoliceMaintenanceDetailDO> {

    default PageResult<TrafficOpsPoliceMaintenanceDetailDO> selectPage(TrafficOpsPoliceMaintenanceDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsPoliceMaintenanceDetailDO>()
                .eqIfPresent(TrafficOpsPoliceMaintenanceDetailDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDetailDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDetailDO::getProblem, reqVO.getProblem())
                .betweenIfPresent(TrafficOpsPoliceMaintenanceDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsPoliceMaintenanceDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsPoliceMaintenanceDetailDO::getId));
    }

}